echo "<div style=\"clear:both; float:left; width: 100%; margin: 0px 0px 0px 0px; font-size:12px;padding: 0px\">";
echo "<div style=\"overflow:hidden; float:left; width: 46%; margin: 0px; padding: 10px;\">";
echo "<p style=\"font-size:large\">家長訊息</p><div class=\"content-txtbox-noshade\">";
include("http://apps.nknush.kh.edu.tw/ZeroBB/Include?p=TAGS&tagname=PARENT");
echo "</div></div>"; 
echo "<div style=\"overflow:hidden; float:left; width: 46%;  margin: 0px; padding: 10px;\">";
echo "<p style=\"font-size:large\">學生活動</p><div class=\"content-txtbox-noshade\">"; 
include("http://apps.nknush.kh.edu.tw/ZeroBB/Include?p=TAGS&tagname=STUDENT");
echo "</div></div>"; 
echo "</div>"; 
echo "<hr>";


echo "<div style=\"clear:both; float:left; width: 100%; margin: 0px 0px 0px 0px; font-size:12px;padding: 0px\">";
echo "<div style=\"overflow:hidden; float:left; width: 46%; margin: 0px; padding: 10px;\">";
echo "<p class=\"content-title-noshade-size1\">教務處公告</p><div class=\"content-txtbox-noshade\">";
include("http://apps.nknush.kh.edu.tw/ZeroBB/Include?p=IMPORTANT&account=jiaowu");
echo "</div></div>"; 
echo "<div style=\"overflow:hidden; float:left; width: 46%;  margin: 0px; padding: 10px;\">";
echo "<p class=\"content-title-noshade-size1\">學務處公告</p><div class=\"content-txtbox-noshade\">"; 
include("http://apps.nknush.kh.edu.tw/ZeroBB/Include?p=IMPORTANT&account=xuewu");
echo "</div></div>"; 
echo "</div>"; 
echo "<hr>"; 

echo "
<div style=\"clear:both; float:left; width: 100%; margin: 0px 0px 0px 0px; font-size:12px; padding: 0px\">";
echo "
<div style=\"overflow:hidden; float:left; width: 46%;  margin: 0px; padding: 10px;\">
<p class=\"content-title-noshade-size1\">輔導室公告</p>
<div class=\"content-txtbox-noshade\">"; include
("http://apps.nknush.kh.edu.tw/ZeroBB/Include?p=IMPORTANT&account=fudao");
echo "</div>
</div>
"; echo "
<div style=\"overflow:hidden; float:left; width: 46%;  margin: 0px; padding: 10px;\">
<p class=\"content-title-noshade-size1\">註冊組公告</p>
<div class=\"content-txtbox-noshade\">"; include
("http://apps.nknush.kh.edu.tw/ZeroBB/Include?p=IMPORTANT&account=zhucezu");
echo "</div>
</div>
"; echo "</div>
"; echo "
<div style=\"clear:both; float:left; width: 100%; margin: 0px 0px 0px 0px; font-size:12px; padding: 0px\">";
echo "
<div style=\"overflow:hidden; float:left; width: 46%;  margin: 0px; padding: 10px;\">
<p class=\"content-title-noshade-size1\">教學組公告</p>
<div class=\"content-txtbox-noshade\">"; include
("http://apps.nknush.kh.edu.tw/ZeroBB/Include?p=IMPORTANT&account=jiaoxue");
echo "</div>
</div>
"; echo "
<div style=\"overflow:hidden; float:left; width: 46%;  margin: 0px; padding: 10px;\">
<p class=\"content-title-noshade-size1\">衛生組公告</p>
<div class=\"content-txtbox-noshade\">"; include
("http://apps.nknush.kh.edu.tw/ZeroBB/Include?p=IMPORTANT&account=weisheng");
echo "</div>
</div>
"; echo "</div>
"; echo "
<hr>
"; echo "
<div style=\"width: 100%\">"; echo "
<div align=\"left\"><a href=\"http://apps.nknush.kh.edu.tw/ZeroBB/?account=jiaowu\">教務處 </a> | <a
	href=\"http://apps.nknush.kh.edu.tw/ZeroBB/?account=xuewu\">學務處 </a> |
<a href=\"http://apps.nknush.kh.edu.tw/ZeroBB/?account=zongwu\">總務處 </a>
| <a href=\"http://apps.nknush.kh.edu.tw/ZeroBB/?account=fudao\">輔導中心</a>
| <a href=\"http://apps.nknush.kh.edu.tw/ZeroBB/?account=renshi\">人事室 </a>
| <a href=\"http://apps.nknush.kh.edu.tw/ZeroBB/?account=kuaiji\">主計室 </a>
| <a href=\"http://apps.nknush.kh.edu.tw/ZeroBB/?account=lib\">圖資中心</a>
| <a href=\"http://apps.nknush.kh.edu.tw/ZeroBB/?account=Documentation\">公文公告區 </a>
| <a href=\"http://apps.nknush.kh.edu.tw/ZeroBB/?account=zhucezu\">教務處註冊組 </a>
| <a href=\"http://apps.nknush.kh.edu.tw/ZeroBB/?account=jiaoxue\">教務處教學組 </a>
| <a href=\"http://apps.nknush.kh.edu.tw/ZeroBB/?account=weisheng\">衛生組 </a>
| <a href=\"http://apps.nknush.kh.edu.tw/ZeroBB/?account=shebei\">教務處設備組</a>
|</div>
"; echo "
<div>
<p align=\"right\"><a href=\"http://apps.nknush.kh.edu.tw/ZeroBB/InsertArticle\" target=\"_blank\">我要公告</a> | <a
	href=\"http://apps.nknush.kh.edu.tw/ZeroBB/\" target=\"_blank\">more...<br />
</a></p>
</div>
"; echo "</div>
";
