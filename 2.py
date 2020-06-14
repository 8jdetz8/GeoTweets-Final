import subprocess

cmd = ['java', 'C:\\Users\\Student\\SDE\\4thTryGeoTweets\\src\\main\\java\\geotweets',
 'C:\\Users\\Student\\LearningJavascript\\LearningFlask\\project\\all_tweets.txt',
 'C:\\Users\\Student\\LearningJavascript\\LearningFlask\\project\\states.csv', str(arg3), arg4]

process = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE)
#process.wait()
f = open("C:\\Users\\Student\\LearningJavascript\\LearningFlask\\project\\tmp")
print('hi')
#print(args[3])
#print(args[4])
result = f.read()
print(result)