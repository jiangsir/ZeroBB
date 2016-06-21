
import os
import fnmatch
import sys
import time

modulename = "ZeroBB"
appname = "ZeroBB_20160621"

for file in os.listdir('/etc/init.d/'):
    if fnmatch.fnmatch(file, 'tomcat*'):
        tomcatN = file

os.system('git clone ssh://git@apps.nknush.kh.edu.tw:22/home/git/repository/ZeroBB.git')
os.system('ant -f ZeroBB/build.xml -Dmodulename=ZeroBB -DTOMCAT_HOME=/usr/share/tomcat7/')

os.system('cp ' + modulename + '.war /var/lib/' + tomcatN + '/webapps/' + appname + '.war')
time.sleep(20)
# check if war file 是否已經完全解開。
os.system('python3 /var/lib/' + tomcatN + '/webapps/' + appname + '/Setup.py')

# 嘗試自動加入 git tag 的版本訊息。
# 或者提供手動輸入的修改介面。
