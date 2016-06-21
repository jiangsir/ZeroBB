import os
import fnmatch
import sys
import time
import subprocess
import datetime

for file in os.listdir('/etc/init.d/'):
    if fnmatch.fnmatch(file, 'tomcat*'):
        tomcatN = file
        
appname = input("請輸入 git host 上的應用程式名稱:")  # ex: ZeroBB
apptmpdir = appname + "_" + datetime.datetime.now().strftime('%Y-%m-%d')

os.system('rm -rf ' + apptmpdir)
os.system('mkdir ' + apptmpdir)

gituri = "ssh://git@git.nknush.kh.edu.tw:22/home/git/repository/" + appname + ".git " + apptmpdir;
choose1 = input("預設 git uri=" + gituri + " (Y/n)")
if choose1 == "n":
    gituri = input("請輸入 git uri=")

os.system('git clone ' + gituri)

pipe = subprocess.Popen('git --git-dir=' + apptmpdir + '/.git" describe', shell=True, stdout=subprocess.PIPE).stdout
tag = str(pipe.read(), 'utf-8')
open(appname + '/WebContent/META-INF/Version.txt', mode='w', encoding='utf-8').write(tag)

for root, dirs, files in os.walk(apptmpdir + "/src/"):
    for file in files:
        if file.endswith(".java"):
             # print(os.path.join(root, file))
             s = open(os.path.join(root, file), mode='r', encoding='utf-8-sig').read()
             open(os.path.join(root, file), mode='w', encoding='utf-8').write(s)


choose2 = input("使用原本的 app name=" + appname + " (Y/n)")
if choose2 == "n":
    appname = input("請輸入本地要發布的的 appname=")  # 如 ROOT

os.system('ant -f ' + apptmpdir + '/build.xml -Dappname=' + appname + ' -DTOMCAT_HOME=/usr/share/' + tomcatN + '/')

os.system('cp ' + appname + '.war /var/lib/' + tomcatN + '/webapps/' + appname + '.war')
time.sleep(20)
# check if war file 是否已經完全解開。
os.system('python3 /var/lib/' + tomcatN + '/webapps/' + appname + '/Setup.py')

# 嘗試自動加入 git tag 的版本訊息。
# 或者提供手動輸入的修改介面。
