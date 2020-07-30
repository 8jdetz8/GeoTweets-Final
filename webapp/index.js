const express = require('express');
const util = require('util');
const exec = util.promisify(require('child_process').exec);

const app = express();

const japp = `${__dirname}/../project`;
const port = 3000;
const cache = {};

app.use(express.static('public'));

app.get('/api/:program/search/:sterm', async (req, res)=>{
    let data = await getData(req.params.program, req.params.sterm);
    res.json(data);
});


function hashtags(line) {
    let col = line.split(' ');
    let obj = {
        date: col.shift(),
        hour: col.shift(),
        val: Number(col.shift())
    }
    return obj;
}

async function getData(program, sterm){
    //  dumb 'cache' for a faster response
    if (cache[program]) {
       if (cache[program][sterm]) return cache[program][sterm];
    }

    const { stdout, stderr } = await exec(
        `java -cp ${japp} geotweets.GeoTweets ${japp}/all_tweets.txt ${japp}/states.csv ${program} '${sterm}'`
        );
    if (stderr) return {error: stderr}
   
    let lines = stdout.split('\n');
    
    lines = lines.filter(el => el !== '').map((line)=>{
        if (program == 3) return hashtags(line);
        let words = line.trim().split(' ');
        let js = {
            id: words.shift(),
            val: Number(words.pop()),
            item: words.join(' ').replace(/\-/, '').trim()
        }
        return js;
    });
    // save on cache
    cache[program] = {
        ...cache[program],
        [sterm]: lines
    }

    return lines;
}

app.listen(port, () => console.log(`GeoTweets app listening at http://localhost:${port}`))