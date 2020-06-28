from flask import Flask, jsonify, render_template, request, Response
import subprocess

app = Flask(__name__)


@app.route('/')
def main():
    return render_template('geoTweetsPage.html')


@app.route('/data')
def second():
    return render_template('geoTweetsPage.html')


@app.route('/data2', methods = ["POST"])
def third():
    import subprocess
    print("hi")
    print(request.form)
    arg3 = int(request.form["third"])
    arg4 = " "
    print("arg3 is " + str(arg3))
    if arg3 != 1:
        arg4 = request.form["fourth"]

    cmd = ['java','-cp', 'C:\\Users\\Student\\LearningJavascript\\LearningFlask\\project', 'geotweets.GeoTweets', 'C:\\Users\\Student\\LearningJavascript\\LearningFlask\\project\\all_tweets.txt', 'C:\\Users\\Student\\LearningJavascript\\LearningFlask\\project\\states.csv', str(arg3), arg4]

    process = subprocess.Popen(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    out, err = process.communicate()
    out = out.decode('utf-8')
    print(out)
    return out


app.run(host='127.0.0.1', port=5000)