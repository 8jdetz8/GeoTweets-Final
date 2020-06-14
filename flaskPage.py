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
    print(request.form)
    arg3 = int(request.form["third"])
    arg4 = " "
    if arg3 != 1:
        arg4 = request.form["fourth"]

    output = subprocess.check_output(
    ['java', 'C:\\Users\\Student\\SDE\\4thTryGeoTweets\\src\\main\\java\\geotweets', 'C:\\Users\\Student\\LearningJavascript\\LearningFlask\\project\\all_tweets.txt', 'C:\\Users\\Student\\LearningJavascript\\LearningFlask\\project\\states.csv', str(arg3), arg4])
    return output


app.run(host='127.0.0.1', port=5000)