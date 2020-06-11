from flask import Flask, jsonify, render_template, request, Response
import subprocess

app = Flask(__name__)


@app.route('/')
def main():
    return render_template('geoTweetsPage.html')


@app.route('/data')
def second():
    return render_template('geoTweetsPage.html')


@app.route('/data2')
def third():
    import subprocess
    arg3 = int(request.data["third"])
    if arg3 == 1:
        // get
        output
        with arg one
    else:
        arg4 = request.data["fourth"]
        // get
        output
        with arg one and four

    output = subprocess.check_output(
    ['java', 'C:\\Users\\Student\\SDE\\4thTryGeoTweets\\src\\main\\java\\geotweets', 'all_tweets.txt', 'states.csv'])
    return output