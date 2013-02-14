#!/bin/bash
cd /home/sdhama/cpe400
/usr/bin/wget -q -O index.html http://www.californiagasprices.com/index.aspx?area=San%20Luis%20Obispo

date > /home/sdhama/www/gas/gasPrices
/usr/bin/java  parseLine >> /home/sdhama/www/gas/gasPrices


head -n 3 /home/sdhama/www/gas/gasPrices >> /home/sdhama/www/gas/historyGas
head -n 3 /home/sdhama/www/gas/gasPrices


/usr/bin/wget -q -O index.html http://www.californiagasprices.com/GasPriceSearch.aspx?fuel=A&qsrch=torrance,ca
javac parseLine1.java 
date > /home/sdhama/www/gas/gasPrices1
/usr/bin/java  parseLine1 >> /home/sdhama/www/gas/gasPrices1

/usr/bin/wget -q -O index.html http://www.sanfrangasprices.com/San%20Francisco/index.aspx
javac parseLine1.java 
date > /home/sdhama/www/gas/gasPrices2
/usr/bin/java  parseLine1 >> /home/sdhama/www/gas/gasPrices2
head -n 3 /home/sdhama/www/gas/gasPrices2 >> /home/sdhama/www/gas/historyGas1
rm index.html

#works!
