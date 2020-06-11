import subprocess

cmd = "java -cp C:\\Users\\Student\\LearningJavascript\\LearningFlask\\project geotweets.GeoTweets C:\\Users\\Student\\LearningJavascript\\LearningFlask\\project\\all_tweets.txt C:\\Users\\Student\\LearningJavascript\\LearningFlask\\project\\states.csv > C:\\Users\\Student\\LearningJavascript\\LearningFlask\\project\\tmp  "

process = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE)
#process.wait()
f = open("C:\\Users\\Student\\LearningJavascript\\LearningFlask\\project\\tmp")
print('hi')
print(args[3])
print(args[4])
result = f.read()
print(result)