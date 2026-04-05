/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
$(document).ready(function() {

    $(".click-title").mouseenter( function(    e){
        e.preventDefault();
        this.style.cursor="pointer";
    });
    $(".click-title").mousedown( function(event){
        event.preventDefault();
    });

    // Ugly code while this script is shared among several pages
    try{
        refreshHitsPerSecond(true);
    } catch(e){}
    try{
        refreshResponseTimeOverTime(true);
    } catch(e){}
    try{
        refreshResponseTimePercentiles();
    } catch(e){}
});


var responseTimePercentilesInfos = {
        data: {"result": {"minY": 62.0, "minX": 0.0, "maxY": 1988.0, "series": [{"data": [[0.0, 62.0], [0.1, 62.0], [0.2, 62.0], [0.3, 62.0], [0.4, 62.0], [0.5, 64.0], [0.6, 64.0], [0.7, 64.0], [0.8, 64.0], [0.9, 64.0], [1.0, 65.0], [1.1, 65.0], [1.2, 65.0], [1.3, 65.0], [1.4, 65.0], [1.5, 66.0], [1.6, 66.0], [1.7, 66.0], [1.8, 66.0], [1.9, 66.0], [2.0, 76.0], [2.1, 76.0], [2.2, 76.0], [2.3, 76.0], [2.4, 76.0], [2.5, 78.0], [2.6, 78.0], [2.7, 78.0], [2.8, 78.0], [2.9, 78.0], [3.0, 93.0], [3.1, 93.0], [3.2, 93.0], [3.3, 93.0], [3.4, 93.0], [3.5, 112.0], [3.6, 112.0], [3.7, 112.0], [3.8, 112.0], [3.9, 112.0], [4.0, 115.0], [4.1, 115.0], [4.2, 115.0], [4.3, 115.0], [4.4, 115.0], [4.5, 115.0], [4.6, 115.0], [4.7, 115.0], [4.8, 115.0], [4.9, 115.0], [5.0, 128.0], [5.1, 128.0], [5.2, 128.0], [5.3, 128.0], [5.4, 128.0], [5.5, 129.0], [5.6, 129.0], [5.7, 129.0], [5.8, 129.0], [5.9, 129.0], [6.0, 130.0], [6.1, 130.0], [6.2, 130.0], [6.3, 130.0], [6.4, 130.0], [6.5, 130.0], [6.6, 130.0], [6.7, 130.0], [6.8, 130.0], [6.9, 130.0], [7.0, 154.0], [7.1, 154.0], [7.2, 154.0], [7.3, 154.0], [7.4, 154.0], [7.5, 156.0], [7.6, 156.0], [7.7, 156.0], [7.8, 156.0], [7.9, 156.0], [8.0, 164.0], [8.1, 164.0], [8.2, 164.0], [8.3, 164.0], [8.4, 164.0], [8.5, 165.0], [8.6, 165.0], [8.7, 165.0], [8.8, 165.0], [8.9, 165.0], [9.0, 165.0], [9.1, 165.0], [9.2, 165.0], [9.3, 165.0], [9.4, 165.0], [9.5, 187.0], [9.6, 187.0], [9.7, 187.0], [9.8, 187.0], [9.9, 187.0], [10.0, 187.0], [10.1, 187.0], [10.2, 187.0], [10.3, 187.0], [10.4, 187.0], [10.5, 196.0], [10.6, 196.0], [10.7, 196.0], [10.8, 196.0], [10.9, 196.0], [11.0, 202.0], [11.1, 202.0], [11.2, 202.0], [11.3, 202.0], [11.4, 202.0], [11.5, 217.0], [11.6, 217.0], [11.7, 217.0], [11.8, 217.0], [11.9, 217.0], [12.0, 218.0], [12.1, 218.0], [12.2, 218.0], [12.3, 218.0], [12.4, 218.0], [12.5, 219.0], [12.6, 219.0], [12.7, 219.0], [12.8, 219.0], [12.9, 219.0], [13.0, 221.0], [13.1, 221.0], [13.2, 221.0], [13.3, 221.0], [13.4, 221.0], [13.5, 226.0], [13.6, 226.0], [13.7, 226.0], [13.8, 226.0], [13.9, 226.0], [14.0, 230.0], [14.1, 230.0], [14.2, 230.0], [14.3, 230.0], [14.4, 230.0], [14.5, 234.0], [14.6, 234.0], [14.7, 234.0], [14.8, 234.0], [14.9, 234.0], [15.0, 257.0], [15.1, 257.0], [15.2, 257.0], [15.3, 257.0], [15.4, 257.0], [15.5, 262.0], [15.6, 262.0], [15.7, 262.0], [15.8, 262.0], [15.9, 262.0], [16.0, 267.0], [16.1, 267.0], [16.2, 267.0], [16.3, 267.0], [16.4, 267.0], [16.5, 276.0], [16.6, 276.0], [16.7, 276.0], [16.8, 276.0], [16.9, 276.0], [17.0, 277.0], [17.1, 277.0], [17.2, 277.0], [17.3, 277.0], [17.4, 277.0], [17.5, 282.0], [17.6, 282.0], [17.7, 282.0], [17.8, 282.0], [17.9, 282.0], [18.0, 301.0], [18.1, 301.0], [18.2, 301.0], [18.3, 301.0], [18.4, 301.0], [18.5, 302.0], [18.6, 302.0], [18.7, 302.0], [18.8, 302.0], [18.9, 302.0], [19.0, 308.0], [19.1, 308.0], [19.2, 308.0], [19.3, 308.0], [19.4, 308.0], [19.5, 319.0], [19.6, 319.0], [19.7, 319.0], [19.8, 319.0], [19.9, 319.0], [20.0, 331.0], [20.1, 331.0], [20.2, 331.0], [20.3, 331.0], [20.4, 331.0], [20.5, 336.0], [20.6, 336.0], [20.7, 336.0], [20.8, 336.0], [20.9, 336.0], [21.0, 340.0], [21.1, 340.0], [21.2, 340.0], [21.3, 340.0], [21.4, 340.0], [21.5, 374.0], [21.6, 374.0], [21.7, 374.0], [21.8, 374.0], [21.9, 374.0], [22.0, 375.0], [22.1, 375.0], [22.2, 375.0], [22.3, 375.0], [22.4, 375.0], [22.5, 380.0], [22.6, 380.0], [22.7, 380.0], [22.8, 380.0], [22.9, 380.0], [23.0, 382.0], [23.1, 382.0], [23.2, 382.0], [23.3, 382.0], [23.4, 382.0], [23.5, 387.0], [23.6, 387.0], [23.7, 387.0], [23.8, 387.0], [23.9, 387.0], [24.0, 388.0], [24.1, 388.0], [24.2, 388.0], [24.3, 388.0], [24.4, 388.0], [24.5, 403.0], [24.6, 403.0], [24.7, 403.0], [24.8, 403.0], [24.9, 403.0], [25.0, 419.0], [25.1, 419.0], [25.2, 419.0], [25.3, 419.0], [25.4, 419.0], [25.5, 419.0], [25.6, 419.0], [25.7, 419.0], [25.8, 419.0], [25.9, 419.0], [26.0, 436.0], [26.1, 436.0], [26.2, 436.0], [26.3, 436.0], [26.4, 436.0], [26.5, 444.0], [26.6, 444.0], [26.7, 444.0], [26.8, 444.0], [26.9, 444.0], [27.0, 464.0], [27.1, 464.0], [27.2, 464.0], [27.3, 464.0], [27.4, 464.0], [27.5, 496.0], [27.6, 496.0], [27.7, 496.0], [27.8, 496.0], [27.9, 496.0], [28.0, 504.0], [28.1, 504.0], [28.2, 504.0], [28.3, 504.0], [28.4, 504.0], [28.5, 515.0], [28.6, 515.0], [28.7, 515.0], [28.8, 515.0], [28.9, 515.0], [29.0, 521.0], [29.1, 521.0], [29.2, 521.0], [29.3, 521.0], [29.4, 521.0], [29.5, 522.0], [29.6, 522.0], [29.7, 522.0], [29.8, 522.0], [29.9, 522.0], [30.0, 524.0], [30.1, 524.0], [30.2, 524.0], [30.3, 524.0], [30.4, 524.0], [30.5, 527.0], [30.6, 527.0], [30.7, 527.0], [30.8, 527.0], [30.9, 527.0], [31.0, 546.0], [31.1, 546.0], [31.2, 546.0], [31.3, 546.0], [31.4, 546.0], [31.5, 553.0], [31.6, 553.0], [31.7, 553.0], [31.8, 553.0], [31.9, 553.0], [32.0, 553.0], [32.1, 553.0], [32.2, 553.0], [32.3, 553.0], [32.4, 553.0], [32.5, 554.0], [32.6, 554.0], [32.7, 554.0], [32.8, 554.0], [32.9, 554.0], [33.0, 557.0], [33.1, 557.0], [33.2, 557.0], [33.3, 557.0], [33.4, 557.0], [33.5, 585.0], [33.6, 585.0], [33.7, 585.0], [33.8, 585.0], [33.9, 585.0], [34.0, 604.0], [34.1, 604.0], [34.2, 604.0], [34.3, 604.0], [34.4, 604.0], [34.5, 610.0], [34.6, 610.0], [34.7, 610.0], [34.8, 610.0], [34.9, 610.0], [35.0, 622.0], [35.1, 622.0], [35.2, 622.0], [35.3, 622.0], [35.4, 622.0], [35.5, 625.0], [35.6, 625.0], [35.7, 625.0], [35.8, 625.0], [35.9, 625.0], [36.0, 641.0], [36.1, 641.0], [36.2, 641.0], [36.3, 641.0], [36.4, 641.0], [36.5, 643.0], [36.6, 643.0], [36.7, 643.0], [36.8, 643.0], [36.9, 643.0], [37.0, 649.0], [37.1, 649.0], [37.2, 649.0], [37.3, 649.0], [37.4, 649.0], [37.5, 658.0], [37.6, 658.0], [37.7, 658.0], [37.8, 658.0], [37.9, 658.0], [38.0, 660.0], [38.1, 660.0], [38.2, 660.0], [38.3, 660.0], [38.4, 660.0], [38.5, 670.0], [38.6, 670.0], [38.7, 670.0], [38.8, 670.0], [38.9, 670.0], [39.0, 687.0], [39.1, 687.0], [39.2, 687.0], [39.3, 687.0], [39.4, 687.0], [39.5, 700.0], [39.6, 700.0], [39.7, 700.0], [39.8, 700.0], [39.9, 700.0], [40.0, 714.0], [40.1, 714.0], [40.2, 714.0], [40.3, 714.0], [40.4, 714.0], [40.5, 719.0], [40.6, 719.0], [40.7, 719.0], [40.8, 719.0], [40.9, 719.0], [41.0, 725.0], [41.1, 725.0], [41.2, 725.0], [41.3, 725.0], [41.4, 725.0], [41.5, 734.0], [41.6, 734.0], [41.7, 734.0], [41.8, 734.0], [41.9, 734.0], [42.0, 745.0], [42.1, 745.0], [42.2, 745.0], [42.3, 745.0], [42.4, 745.0], [42.5, 762.0], [42.6, 762.0], [42.7, 762.0], [42.8, 762.0], [42.9, 762.0], [43.0, 765.0], [43.1, 765.0], [43.2, 765.0], [43.3, 765.0], [43.4, 765.0], [43.5, 767.0], [43.6, 767.0], [43.7, 767.0], [43.8, 767.0], [43.9, 767.0], [44.0, 784.0], [44.1, 784.0], [44.2, 784.0], [44.3, 784.0], [44.4, 784.0], [44.5, 785.0], [44.6, 785.0], [44.7, 785.0], [44.8, 785.0], [44.9, 785.0], [45.0, 793.0], [45.1, 793.0], [45.2, 793.0], [45.3, 793.0], [45.4, 793.0], [45.5, 795.0], [45.6, 795.0], [45.7, 795.0], [45.8, 795.0], [45.9, 795.0], [46.0, 806.0], [46.1, 806.0], [46.2, 806.0], [46.3, 806.0], [46.4, 806.0], [46.5, 814.0], [46.6, 814.0], [46.7, 814.0], [46.8, 814.0], [46.9, 814.0], [47.0, 816.0], [47.1, 816.0], [47.2, 816.0], [47.3, 816.0], [47.4, 816.0], [47.5, 820.0], [47.6, 820.0], [47.7, 820.0], [47.8, 820.0], [47.9, 820.0], [48.0, 836.0], [48.1, 836.0], [48.2, 836.0], [48.3, 836.0], [48.4, 836.0], [48.5, 837.0], [48.6, 837.0], [48.7, 837.0], [48.8, 837.0], [48.9, 837.0], [49.0, 860.0], [49.1, 860.0], [49.2, 860.0], [49.3, 860.0], [49.4, 860.0], [49.5, 863.0], [49.6, 863.0], [49.7, 863.0], [49.8, 863.0], [49.9, 863.0], [50.0, 867.0], [50.1, 867.0], [50.2, 867.0], [50.3, 867.0], [50.4, 867.0], [50.5, 871.0], [50.6, 871.0], [50.7, 871.0], [50.8, 871.0], [50.9, 871.0], [51.0, 886.0], [51.1, 886.0], [51.2, 886.0], [51.3, 886.0], [51.4, 886.0], [51.5, 891.0], [51.6, 891.0], [51.7, 891.0], [51.8, 891.0], [51.9, 891.0], [52.0, 904.0], [52.1, 904.0], [52.2, 904.0], [52.3, 904.0], [52.4, 904.0], [52.5, 914.0], [52.6, 914.0], [52.7, 914.0], [52.8, 914.0], [52.9, 914.0], [53.0, 923.0], [53.1, 923.0], [53.2, 923.0], [53.3, 923.0], [53.4, 923.0], [53.5, 924.0], [53.6, 924.0], [53.7, 924.0], [53.8, 924.0], [53.9, 924.0], [54.0, 927.0], [54.1, 927.0], [54.2, 927.0], [54.3, 927.0], [54.4, 927.0], [54.5, 957.0], [54.6, 957.0], [54.7, 957.0], [54.8, 957.0], [54.9, 957.0], [55.0, 966.0], [55.1, 966.0], [55.2, 966.0], [55.3, 966.0], [55.4, 966.0], [55.5, 974.0], [55.6, 974.0], [55.7, 974.0], [55.8, 974.0], [55.9, 974.0], [56.0, 991.0], [56.1, 991.0], [56.2, 991.0], [56.3, 991.0], [56.4, 991.0], [56.5, 997.0], [56.6, 997.0], [56.7, 997.0], [56.8, 997.0], [56.9, 997.0], [57.0, 1069.0], [57.1, 1069.0], [57.2, 1069.0], [57.3, 1069.0], [57.4, 1069.0], [57.5, 1110.0], [57.6, 1110.0], [57.7, 1110.0], [57.8, 1110.0], [57.9, 1110.0], [58.0, 1116.0], [58.1, 1116.0], [58.2, 1116.0], [58.3, 1116.0], [58.4, 1116.0], [58.5, 1126.0], [58.6, 1126.0], [58.7, 1126.0], [58.8, 1126.0], [58.9, 1126.0], [59.0, 1139.0], [59.1, 1139.0], [59.2, 1139.0], [59.3, 1139.0], [59.4, 1139.0], [59.5, 1176.0], [59.6, 1176.0], [59.7, 1176.0], [59.8, 1176.0], [59.9, 1176.0], [60.0, 1177.0], [60.1, 1177.0], [60.2, 1177.0], [60.3, 1177.0], [60.4, 1177.0], [60.5, 1184.0], [60.6, 1184.0], [60.7, 1184.0], [60.8, 1184.0], [60.9, 1184.0], [61.0, 1185.0], [61.1, 1185.0], [61.2, 1185.0], [61.3, 1185.0], [61.4, 1185.0], [61.5, 1204.0], [61.6, 1204.0], [61.7, 1204.0], [61.8, 1204.0], [61.9, 1204.0], [62.0, 1211.0], [62.1, 1211.0], [62.2, 1211.0], [62.3, 1211.0], [62.4, 1211.0], [62.5, 1211.0], [62.6, 1211.0], [62.7, 1211.0], [62.8, 1211.0], [62.9, 1211.0], [63.0, 1228.0], [63.1, 1228.0], [63.2, 1228.0], [63.3, 1228.0], [63.4, 1228.0], [63.5, 1235.0], [63.6, 1235.0], [63.7, 1235.0], [63.8, 1235.0], [63.9, 1235.0], [64.0, 1238.0], [64.1, 1238.0], [64.2, 1238.0], [64.3, 1238.0], [64.4, 1238.0], [64.5, 1253.0], [64.6, 1253.0], [64.7, 1253.0], [64.8, 1253.0], [64.9, 1253.0], [65.0, 1263.0], [65.1, 1263.0], [65.2, 1263.0], [65.3, 1263.0], [65.4, 1263.0], [65.5, 1283.0], [65.6, 1283.0], [65.7, 1283.0], [65.8, 1283.0], [65.9, 1283.0], [66.0, 1283.0], [66.1, 1283.0], [66.2, 1283.0], [66.3, 1283.0], [66.4, 1283.0], [66.5, 1298.0], [66.6, 1298.0], [66.7, 1298.0], [66.8, 1298.0], [66.9, 1298.0], [67.0, 1318.0], [67.1, 1318.0], [67.2, 1318.0], [67.3, 1318.0], [67.4, 1318.0], [67.5, 1330.0], [67.6, 1330.0], [67.7, 1330.0], [67.8, 1330.0], [67.9, 1330.0], [68.0, 1335.0], [68.1, 1335.0], [68.2, 1335.0], [68.3, 1335.0], [68.4, 1335.0], [68.5, 1346.0], [68.6, 1346.0], [68.7, 1346.0], [68.8, 1346.0], [68.9, 1346.0], [69.0, 1353.0], [69.1, 1353.0], [69.2, 1353.0], [69.3, 1353.0], [69.4, 1353.0], [69.5, 1356.0], [69.6, 1356.0], [69.7, 1356.0], [69.8, 1356.0], [69.9, 1356.0], [70.0, 1359.0], [70.1, 1359.0], [70.2, 1359.0], [70.3, 1359.0], [70.4, 1359.0], [70.5, 1366.0], [70.6, 1366.0], [70.7, 1366.0], [70.8, 1366.0], [70.9, 1366.0], [71.0, 1366.0], [71.1, 1366.0], [71.2, 1366.0], [71.3, 1366.0], [71.4, 1366.0], [71.5, 1369.0], [71.6, 1369.0], [71.7, 1369.0], [71.8, 1369.0], [71.9, 1369.0], [72.0, 1384.0], [72.1, 1384.0], [72.2, 1384.0], [72.3, 1384.0], [72.4, 1384.0], [72.5, 1411.0], [72.6, 1411.0], [72.7, 1411.0], [72.8, 1411.0], [72.9, 1411.0], [73.0, 1414.0], [73.1, 1414.0], [73.2, 1414.0], [73.3, 1414.0], [73.4, 1414.0], [73.5, 1511.0], [73.6, 1511.0], [73.7, 1511.0], [73.8, 1511.0], [73.9, 1511.0], [74.0, 1511.0], [74.1, 1511.0], [74.2, 1511.0], [74.3, 1511.0], [74.4, 1511.0], [74.5, 1513.0], [74.6, 1513.0], [74.7, 1513.0], [74.8, 1513.0], [74.9, 1513.0], [75.0, 1515.0], [75.1, 1515.0], [75.2, 1515.0], [75.3, 1515.0], [75.4, 1515.0], [75.5, 1530.0], [75.6, 1530.0], [75.7, 1530.0], [75.8, 1530.0], [75.9, 1530.0], [76.0, 1530.0], [76.1, 1530.0], [76.2, 1530.0], [76.3, 1530.0], [76.4, 1530.0], [76.5, 1553.0], [76.6, 1553.0], [76.7, 1553.0], [76.8, 1553.0], [76.9, 1553.0], [77.0, 1565.0], [77.1, 1565.0], [77.2, 1565.0], [77.3, 1565.0], [77.4, 1565.0], [77.5, 1571.0], [77.6, 1571.0], [77.7, 1571.0], [77.8, 1571.0], [77.9, 1571.0], [78.0, 1574.0], [78.1, 1574.0], [78.2, 1574.0], [78.3, 1574.0], [78.4, 1574.0], [78.5, 1574.0], [78.6, 1574.0], [78.7, 1574.0], [78.8, 1574.0], [78.9, 1574.0], [79.0, 1577.0], [79.1, 1577.0], [79.2, 1577.0], [79.3, 1577.0], [79.4, 1577.0], [79.5, 1579.0], [79.6, 1579.0], [79.7, 1579.0], [79.8, 1579.0], [79.9, 1579.0], [80.0, 1579.0], [80.1, 1579.0], [80.2, 1579.0], [80.3, 1579.0], [80.4, 1579.0], [80.5, 1586.0], [80.6, 1586.0], [80.7, 1586.0], [80.8, 1586.0], [80.9, 1586.0], [81.0, 1604.0], [81.1, 1604.0], [81.2, 1604.0], [81.3, 1604.0], [81.4, 1604.0], [81.5, 1614.0], [81.6, 1614.0], [81.7, 1614.0], [81.8, 1614.0], [81.9, 1614.0], [82.0, 1633.0], [82.1, 1633.0], [82.2, 1633.0], [82.3, 1633.0], [82.4, 1633.0], [82.5, 1637.0], [82.6, 1637.0], [82.7, 1637.0], [82.8, 1637.0], [82.9, 1637.0], [83.0, 1638.0], [83.1, 1638.0], [83.2, 1638.0], [83.3, 1638.0], [83.4, 1638.0], [83.5, 1642.0], [83.6, 1642.0], [83.7, 1642.0], [83.8, 1642.0], [83.9, 1642.0], [84.0, 1665.0], [84.1, 1665.0], [84.2, 1665.0], [84.3, 1665.0], [84.4, 1665.0], [84.5, 1667.0], [84.6, 1667.0], [84.7, 1667.0], [84.8, 1667.0], [84.9, 1667.0], [85.0, 1668.0], [85.1, 1668.0], [85.2, 1668.0], [85.3, 1668.0], [85.4, 1668.0], [85.5, 1672.0], [85.6, 1672.0], [85.7, 1672.0], [85.8, 1672.0], [85.9, 1672.0], [86.0, 1679.0], [86.1, 1679.0], [86.2, 1679.0], [86.3, 1679.0], [86.4, 1679.0], [86.5, 1681.0], [86.6, 1681.0], [86.7, 1681.0], [86.8, 1681.0], [86.9, 1681.0], [87.0, 1683.0], [87.1, 1683.0], [87.2, 1683.0], [87.3, 1683.0], [87.4, 1683.0], [87.5, 1688.0], [87.6, 1688.0], [87.7, 1688.0], [87.8, 1688.0], [87.9, 1688.0], [88.0, 1721.0], [88.1, 1721.0], [88.2, 1721.0], [88.3, 1721.0], [88.4, 1721.0], [88.5, 1738.0], [88.6, 1738.0], [88.7, 1738.0], [88.8, 1738.0], [88.9, 1738.0], [89.0, 1744.0], [89.1, 1744.0], [89.2, 1744.0], [89.3, 1744.0], [89.4, 1744.0], [89.5, 1784.0], [89.6, 1784.0], [89.7, 1784.0], [89.8, 1784.0], [89.9, 1784.0], [90.0, 1791.0], [90.1, 1791.0], [90.2, 1791.0], [90.3, 1791.0], [90.4, 1791.0], [90.5, 1799.0], [90.6, 1799.0], [90.7, 1799.0], [90.8, 1799.0], [90.9, 1799.0], [91.0, 1805.0], [91.1, 1805.0], [91.2, 1805.0], [91.3, 1805.0], [91.4, 1805.0], [91.5, 1807.0], [91.6, 1807.0], [91.7, 1807.0], [91.8, 1807.0], [91.9, 1807.0], [92.0, 1811.0], [92.1, 1811.0], [92.2, 1811.0], [92.3, 1811.0], [92.4, 1811.0], [92.5, 1816.0], [92.6, 1816.0], [92.7, 1816.0], [92.8, 1816.0], [92.9, 1816.0], [93.0, 1838.0], [93.1, 1838.0], [93.2, 1838.0], [93.3, 1838.0], [93.4, 1838.0], [93.5, 1839.0], [93.6, 1839.0], [93.7, 1839.0], [93.8, 1839.0], [93.9, 1839.0], [94.0, 1842.0], [94.1, 1842.0], [94.2, 1842.0], [94.3, 1842.0], [94.4, 1842.0], [94.5, 1844.0], [94.6, 1844.0], [94.7, 1844.0], [94.8, 1844.0], [94.9, 1844.0], [95.0, 1886.0], [95.1, 1886.0], [95.2, 1886.0], [95.3, 1886.0], [95.4, 1886.0], [95.5, 1902.0], [95.6, 1902.0], [95.7, 1902.0], [95.8, 1902.0], [95.9, 1902.0], [96.0, 1907.0], [96.1, 1907.0], [96.2, 1907.0], [96.3, 1907.0], [96.4, 1907.0], [96.5, 1910.0], [96.6, 1910.0], [96.7, 1910.0], [96.8, 1910.0], [96.9, 1910.0], [97.0, 1923.0], [97.1, 1923.0], [97.2, 1923.0], [97.3, 1923.0], [97.4, 1923.0], [97.5, 1931.0], [97.6, 1931.0], [97.7, 1931.0], [97.8, 1931.0], [97.9, 1931.0], [98.0, 1940.0], [98.1, 1940.0], [98.2, 1940.0], [98.3, 1940.0], [98.4, 1940.0], [98.5, 1941.0], [98.6, 1941.0], [98.7, 1941.0], [98.8, 1941.0], [98.9, 1941.0], [99.0, 1953.0], [99.1, 1953.0], [99.2, 1953.0], [99.3, 1953.0], [99.4, 1953.0], [99.5, 1988.0], [99.6, 1988.0], [99.7, 1988.0], [99.8, 1988.0], [99.9, 1988.0]], "isOverall": false, "label": "HTTP Request", "isController": false}], "supportsControllersDiscrimination": true, "maxX": 100.0, "title": "Response Time Percentiles"}},
        getOptions: function() {
            return {
                series: {
                    points: { show: false }
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimePercentiles'
                },
                xaxis: {
                    tickDecimals: 1,
                    axisLabel: "Percentiles",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Percentile value in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : %x.2 percentile was %y ms"
                },
                selection: { mode: "xy" },
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimePercentiles"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimesPercentiles"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimesPercentiles"), dataset, prepareOverviewOptions(options));
        }
};

/**
 * @param elementId Id of element where we display message
 */
function setEmptyGraph(elementId) {
    $(function() {
        $(elementId).text("No graph series with filter="+seriesFilter);
    });
}

// Response times percentiles
function refreshResponseTimePercentiles() {
    var infos = responseTimePercentilesInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimePercentiles");
        return;
    }
    if (isGraph($("#flotResponseTimesPercentiles"))){
        infos.createGraph();
    } else {
        var choiceContainer = $("#choicesResponseTimePercentiles");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimesPercentiles", "#overviewResponseTimesPercentiles");
        $('#bodyResponseTimePercentiles .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var responseTimeDistributionInfos = {
        data: {"result": {"minY": 1.0, "minX": 0.0, "maxY": 15.0, "series": [{"data": [[0.0, 7.0], [600.0, 11.0], [700.0, 13.0], [200.0, 14.0], [800.0, 12.0], [900.0, 10.0], [1000.0, 1.0], [1100.0, 8.0], [300.0, 13.0], [1200.0, 11.0], [1300.0, 11.0], [1400.0, 2.0], [1500.0, 15.0], [100.0, 15.0], [400.0, 7.0], [1600.0, 14.0], [1700.0, 6.0], [1800.0, 9.0], [1900.0, 9.0], [500.0, 12.0]], "isOverall": false, "label": "HTTP Request", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 100, "maxX": 1900.0, "title": "Response Time Distribution"}},
        getOptions: function() {
            var granularity = this.data.result.granularity;
            return {
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimeDistribution'
                },
                xaxis:{
                    axisLabel: "Response times in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of responses",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                bars : {
                    show: true,
                    barWidth: this.data.result.granularity
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: function(label, xval, yval, flotItem){
                        return yval + " responses for " + label + " were between " + xval + " and " + (xval + granularity) + " ms";
                    }
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimeDistribution"), prepareData(data.result.series, $("#choicesResponseTimeDistribution")), options);
        }

};

// Response time distribution
function refreshResponseTimeDistribution() {
    var infos = responseTimeDistributionInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimeDistribution");
        return;
    }
    if (isGraph($("#flotResponseTimeDistribution"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimeDistribution");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        $('#footerResponseTimeDistribution .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};


var syntheticResponseTimeDistributionInfos = {
        data: {"result": {"minY": 53.0, "minX": 0.0, "ticks": [[0, "Requests having \nresponse time <= 500ms"], [1, "Requests having \nresponse time > 500ms and <= 1,500ms"], [2, "Requests having \nresponse time > 1,500ms"], [3, "Requests in error"]], "maxY": 91.0, "series": [{"data": [[0.0, 56.0]], "color": "#9ACD32", "isOverall": false, "label": "Requests having \nresponse time <= 500ms", "isController": false}, {"data": [[1.0, 91.0]], "color": "yellow", "isOverall": false, "label": "Requests having \nresponse time > 500ms and <= 1,500ms", "isController": false}, {"data": [[2.0, 53.0]], "color": "orange", "isOverall": false, "label": "Requests having \nresponse time > 1,500ms", "isController": false}, {"data": [], "color": "#FF6347", "isOverall": false, "label": "Requests in error", "isController": false}], "supportsControllersDiscrimination": false, "maxX": 2.0, "title": "Synthetic Response Times Distribution"}},
        getOptions: function() {
            return {
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendSyntheticResponseTimeDistribution'
                },
                xaxis:{
                    axisLabel: "Response times ranges",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                    tickLength:0,
                    min:-0.5,
                    max:3.5
                },
                yaxis: {
                    axisLabel: "Number of responses",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                bars : {
                    show: true,
                    align: "center",
                    barWidth: 0.25,
                    fill:.75
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: function(label, xval, yval, flotItem){
                        return yval + " " + label;
                    }
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var options = this.getOptions();
            prepareOptions(options, data);
            options.xaxis.ticks = data.result.ticks;
            $.plot($("#flotSyntheticResponseTimeDistribution"), prepareData(data.result.series, $("#choicesSyntheticResponseTimeDistribution")), options);
        }

};

// Response time distribution
function refreshSyntheticResponseTimeDistribution() {
    var infos = syntheticResponseTimeDistributionInfos;
    prepareSeries(infos.data, true);
    if (isGraph($("#flotSyntheticResponseTimeDistribution"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesSyntheticResponseTimeDistribution");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        $('#footerSyntheticResponseTimeDistribution .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var activeThreadsOverTimeInfos = {
        data: {"result": {"minY": 65.40500000000006, "minX": 1.7754063E12, "maxY": 65.40500000000006, "series": [{"data": [[1.7754063E12, 65.40500000000006]], "isOverall": false, "label": "users", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.7754063E12, "title": "Active Threads Over Time"}},
        getOptions: function() {
            return {
                series: {
                    stack: true,
                    lines: {
                        show: true,
                        fill: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of active threads",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 6,
                    show: true,
                    container: '#legendActiveThreadsOverTime'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                selection: {
                    mode: 'xy'
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : At %x there were %y active threads"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesActiveThreadsOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotActiveThreadsOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewActiveThreadsOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Active Threads Over Time
function refreshActiveThreadsOverTime(fixTimestamps) {
    var infos = activeThreadsOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotActiveThreadsOverTime"))) {
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesActiveThreadsOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotActiveThreadsOverTime", "#overviewActiveThreadsOverTime");
        $('#footerActiveThreadsOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var timeVsThreadsInfos = {
        data: {"result": {"minY": 63.0, "minX": 1.0, "maxY": 1988.0, "series": [{"data": [[2.0, 1842.0], [3.0, 1902.0], [4.0, 1910.0], [5.0, 1838.0], [6.0, 1931.0], [7.0, 1907.0], [8.0, 1679.0], [9.0, 1683.0], [10.0, 1784.0], [11.0, 1721.0], [12.0, 1744.0], [13.0, 1811.0], [14.0, 1816.0], [15.0, 1791.0], [16.0, 1953.0], [17.0, 1941.0], [18.0, 1665.0], [20.0, 1147.3333333333333], [21.0, 609.6666666666666], [22.0, 63.0], [23.0, 1706.5], [24.0, 1988.0], [25.0, 1681.0], [26.0, 828.5], [28.0, 867.0], [29.0, 130.0], [30.0, 1720.0], [31.0, 1000.5], [33.0, 593.25], [32.0, 1530.0], [35.0, 568.5], [34.0, 1923.0], [36.0, 662.6666666666667], [37.0, 896.0], [38.0, 908.0], [39.0, 1414.0], [40.0, 164.0], [41.0, 1541.0], [43.0, 996.5], [42.0, 1335.0], [44.0, 925.5], [45.0, 934.0], [46.0, 529.0], [47.0, 793.0], [49.0, 824.0], [48.0, 1667.0], [50.0, 476.75], [51.0, 1411.0], [53.0, 817.5], [52.0, 1511.0], [55.0, 833.0], [54.0, 1579.0], [57.0, 1639.5], [59.0, 308.0], [58.0, 1515.0], [60.0, 1084.0], [61.0, 1553.0], [63.0, 1298.0], [62.0, 1283.0], [64.0, 858.5], [67.0, 705.5], [66.0, 1346.0], [65.0, 1211.0], [70.0, 843.0], [69.0, 720.0], [71.0, 1184.0], [68.0, 1263.0], [74.0, 779.0], [75.0, 589.0], [73.0, 1253.0], [72.0, 1513.0], [76.0, 592.0], [78.0, 340.0], [79.0, 1202.5], [77.0, 1353.0], [83.0, 1211.0], [82.0, 1235.0], [81.0, 1283.0], [80.0, 1126.0], [86.0, 644.3333333333334], [87.0, 997.0], [85.0, 1116.0], [84.0, 957.0], [88.0, 744.0], [90.0, 789.0], [89.0, 549.6666666666666], [91.0, 871.0], [95.0, 582.25], [94.0, 974.0], [93.0, 814.0], [92.0, 891.0], [99.0, 923.0], [98.0, 914.0], [97.0, 820.0], [96.0, 860.0], [100.0, 593.3333333333334], [103.0, 991.0], [102.0, 966.0], [101.0, 658.0], [104.0, 661.0], [106.0, 691.0], [107.0, 863.0], [105.0, 904.0], [111.0, 662.0], [110.0, 762.0], [109.0, 649.0], [108.0, 793.0], [113.0, 652.6666666666666], [112.0, 605.3333333333334], [115.0, 785.0], [114.0, 725.0], [117.0, 658.6666666666666], [116.0, 726.5], [118.0, 720.0], [119.0, 660.0], [121.0, 638.6666666666666], [122.0, 637.0], [123.0, 610.0], [120.0, 806.0], [125.0, 643.0], [124.0, 553.0], [1.0, 1805.0]], "isOverall": false, "label": "HTTP Request", "isController": false}, {"data": [[65.40500000000006, 946.4300000000003]], "isOverall": false, "label": "HTTP Request-Aggregated", "isController": false}], "supportsControllersDiscrimination": true, "maxX": 125.0, "title": "Time VS Threads"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    axisLabel: "Number of active threads",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response times in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: { noColumns: 2,show: true, container: '#legendTimeVsThreads' },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s: At %x.2 active threads, Average response time was %y.2 ms"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesTimeVsThreads"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotTimesVsThreads"), dataset, options);
            // setup overview
            $.plot($("#overviewTimesVsThreads"), dataset, prepareOverviewOptions(options));
        }
};

// Time vs threads
function refreshTimeVsThreads(){
    var infos = timeVsThreadsInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyTimeVsThreads");
        return;
    }
    if(isGraph($("#flotTimesVsThreads"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTimeVsThreads");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTimesVsThreads", "#overviewTimesVsThreads");
        $('#footerTimeVsThreads .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var bytesThroughputOverTimeInfos = {
        data : {"result": {"minY": 466.6666666666667, "minX": 1.7754063E12, "maxY": 986.6666666666666, "series": [{"data": [[1.7754063E12, 986.6666666666666]], "isOverall": false, "label": "Bytes received per second", "isController": false}, {"data": [[1.7754063E12, 466.6666666666667]], "isOverall": false, "label": "Bytes sent per second", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.7754063E12, "title": "Bytes Throughput Over Time"}},
        getOptions : function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity) ,
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Bytes / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendBytesThroughputOverTime'
                },
                selection: {
                    mode: "xy"
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y"
                }
            };
        },
        createGraph : function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesBytesThroughputOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotBytesThroughputOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewBytesThroughputOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Bytes throughput Over Time
function refreshBytesThroughputOverTime(fixTimestamps) {
    var infos = bytesThroughputOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotBytesThroughputOverTime"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesBytesThroughputOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotBytesThroughputOverTime", "#overviewBytesThroughputOverTime");
        $('#footerBytesThroughputOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var responseTimesOverTimeInfos = {
        data: {"result": {"minY": 946.4300000000003, "minX": 1.7754063E12, "maxY": 946.4300000000003, "series": [{"data": [[1.7754063E12, 946.4300000000003]], "isOverall": false, "label": "HTTP Request", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.7754063E12, "title": "Response Time Over Time"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average response time was %y ms"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Response Times Over Time
function refreshResponseTimeOverTime(fixTimestamps) {
    var infos = responseTimesOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimeOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotResponseTimesOverTime"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimesOverTime", "#overviewResponseTimesOverTime");
        $('#footerResponseTimesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var latenciesOverTimeInfos = {
        data: {"result": {"minY": 946.3800000000003, "minX": 1.7754063E12, "maxY": 946.3800000000003, "series": [{"data": [[1.7754063E12, 946.3800000000003]], "isOverall": false, "label": "HTTP Request", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.7754063E12, "title": "Latencies Over Time"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response latencies in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendLatenciesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average latency was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesLatenciesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotLatenciesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewLatenciesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Latencies Over Time
function refreshLatenciesOverTime(fixTimestamps) {
    var infos = latenciesOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyLatenciesOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotLatenciesOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesLatenciesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotLatenciesOverTime", "#overviewLatenciesOverTime");
        $('#footerLatenciesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var connectTimeOverTimeInfos = {
        data: {"result": {"minY": 1.14, "minX": 1.7754063E12, "maxY": 1.14, "series": [{"data": [[1.7754063E12, 1.14]], "isOverall": false, "label": "HTTP Request", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.7754063E12, "title": "Connect Time Over Time"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getConnectTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average Connect Time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendConnectTimeOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average connect time was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesConnectTimeOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotConnectTimeOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewConnectTimeOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Connect Time Over Time
function refreshConnectTimeOverTime(fixTimestamps) {
    var infos = connectTimeOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyConnectTimeOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotConnectTimeOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesConnectTimeOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotConnectTimeOverTime", "#overviewConnectTimeOverTime");
        $('#footerConnectTimeOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var responseTimePercentilesOverTimeInfos = {
        data: {"result": {"minY": 62.0, "minX": 1.7754063E12, "maxY": 1988.0, "series": [{"data": [[1.7754063E12, 1988.0]], "isOverall": false, "label": "Max", "isController": false}, {"data": [[1.7754063E12, 62.0]], "isOverall": false, "label": "Min", "isController": false}, {"data": [[1.7754063E12, 1790.3]], "isOverall": false, "label": "90th percentile", "isController": false}, {"data": [[1.7754063E12, 1952.88]], "isOverall": false, "label": "99th percentile", "isController": false}, {"data": [[1.7754063E12, 865.0]], "isOverall": false, "label": "Median", "isController": false}, {"data": [[1.7754063E12, 1883.8999999999996]], "isOverall": false, "label": "95th percentile", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.7754063E12, "title": "Response Time Percentiles Over Time (successful requests only)"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true,
                        fill: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Response Time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimePercentilesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Response time was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimePercentilesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimePercentilesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimePercentilesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Response Time Percentiles Over Time
function refreshResponseTimePercentilesOverTime(fixTimestamps) {
    var infos = responseTimePercentilesOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotResponseTimePercentilesOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesResponseTimePercentilesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimePercentilesOverTime", "#overviewResponseTimePercentilesOverTime");
        $('#footerResponseTimePercentilesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};


var responseTimeVsRequestInfos = {
    data: {"result": {"minY": 78.0, "minX": 11.0, "maxY": 1683.0, "series": [{"data": [[76.0, 395.5], [11.0, 78.0], [51.0, 1683.0], [62.0, 1132.5]], "isOverall": false, "label": "Successes", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 1000, "maxX": 76.0, "title": "Response Time Vs Request"}},
    getOptions: function() {
        return {
            series: {
                lines: {
                    show: false
                },
                points: {
                    show: true
                }
            },
            xaxis: {
                axisLabel: "Global number of requests per second",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            yaxis: {
                axisLabel: "Median Response Time in ms",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            legend: {
                noColumns: 2,
                show: true,
                container: '#legendResponseTimeVsRequest'
            },
            selection: {
                mode: 'xy'
            },
            grid: {
                hoverable: true // IMPORTANT! this is needed for tooltip to work
            },
            tooltip: true,
            tooltipOpts: {
                content: "%s : Median response time at %x req/s was %y ms"
            },
            colors: ["#9ACD32", "#FF6347"]
        };
    },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesResponseTimeVsRequest"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotResponseTimeVsRequest"), dataset, options);
        // setup overview
        $.plot($("#overviewResponseTimeVsRequest"), dataset, prepareOverviewOptions(options));

    }
};

// Response Time vs Request
function refreshResponseTimeVsRequest() {
    var infos = responseTimeVsRequestInfos;
    prepareSeries(infos.data);
    if (isGraph($("#flotResponseTimeVsRequest"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimeVsRequest");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimeVsRequest", "#overviewResponseTimeVsRequest");
        $('#footerResponseRimeVsRequest .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};


var latenciesVsRequestInfos = {
    data: {"result": {"minY": 78.0, "minX": 11.0, "maxY": 1683.0, "series": [{"data": [[76.0, 395.5], [11.0, 78.0], [51.0, 1683.0], [62.0, 1132.5]], "isOverall": false, "label": "Successes", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 1000, "maxX": 76.0, "title": "Latencies Vs Request"}},
    getOptions: function() {
        return{
            series: {
                lines: {
                    show: false
                },
                points: {
                    show: true
                }
            },
            xaxis: {
                axisLabel: "Global number of requests per second",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            yaxis: {
                axisLabel: "Median Latency in ms",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            legend: { noColumns: 2,show: true, container: '#legendLatencyVsRequest' },
            selection: {
                mode: 'xy'
            },
            grid: {
                hoverable: true // IMPORTANT! this is needed for tooltip to work
            },
            tooltip: true,
            tooltipOpts: {
                content: "%s : Median Latency time at %x req/s was %y ms"
            },
            colors: ["#9ACD32", "#FF6347"]
        };
    },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesLatencyVsRequest"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotLatenciesVsRequest"), dataset, options);
        // setup overview
        $.plot($("#overviewLatenciesVsRequest"), dataset, prepareOverviewOptions(options));
    }
};

// Latencies vs Request
function refreshLatenciesVsRequest() {
        var infos = latenciesVsRequestInfos;
        prepareSeries(infos.data);
        if(isGraph($("#flotLatenciesVsRequest"))){
            infos.createGraph();
        }else{
            var choiceContainer = $("#choicesLatencyVsRequest");
            createLegend(choiceContainer, infos);
            infos.createGraph();
            setGraphZoomable("#flotLatenciesVsRequest", "#overviewLatenciesVsRequest");
            $('#footerLatenciesVsRequest .legendColorBox > div').each(function(i){
                $(this).clone().prependTo(choiceContainer.find("li").eq(i));
            });
        }
};

var hitsPerSecondInfos = {
        data: {"result": {"minY": 3.3333333333333335, "minX": 1.7754063E12, "maxY": 3.3333333333333335, "series": [{"data": [[1.7754063E12, 3.3333333333333335]], "isOverall": false, "label": "hitsPerSecond", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.7754063E12, "title": "Hits Per Second"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of hits / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendHitsPerSecond"
                },
                selection: {
                    mode : 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y.2 hits/sec"
                }
            };
        },
        createGraph: function createGraph() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesHitsPerSecond"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotHitsPerSecond"), dataset, options);
            // setup overview
            $.plot($("#overviewHitsPerSecond"), dataset, prepareOverviewOptions(options));
        }
};

// Hits per second
function refreshHitsPerSecond(fixTimestamps) {
    var infos = hitsPerSecondInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if (isGraph($("#flotHitsPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesHitsPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotHitsPerSecond", "#overviewHitsPerSecond");
        $('#footerHitsPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var codesPerSecondInfos = {
        data: {"result": {"minY": 3.3333333333333335, "minX": 1.7754063E12, "maxY": 3.3333333333333335, "series": [{"data": [[1.7754063E12, 3.3333333333333335]], "isOverall": false, "label": "200", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.7754063E12, "title": "Codes Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of responses / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendCodesPerSecond"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "Number of Response Codes %s at %x was %y.2 responses / sec"
                }
            };
        },
    createGraph: function() {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesCodesPerSecond"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotCodesPerSecond"), dataset, options);
        // setup overview
        $.plot($("#overviewCodesPerSecond"), dataset, prepareOverviewOptions(options));
    }
};

// Codes per second
function refreshCodesPerSecond(fixTimestamps) {
    var infos = codesPerSecondInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotCodesPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesCodesPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotCodesPerSecond", "#overviewCodesPerSecond");
        $('#footerCodesPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var transactionsPerSecondInfos = {
        data: {"result": {"minY": 3.3333333333333335, "minX": 1.7754063E12, "maxY": 3.3333333333333335, "series": [{"data": [[1.7754063E12, 3.3333333333333335]], "isOverall": false, "label": "HTTP Request-success", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.7754063E12, "title": "Transactions Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of transactions / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendTransactionsPerSecond"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y transactions / sec"
                }
            };
        },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesTransactionsPerSecond"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotTransactionsPerSecond"), dataset, options);
        // setup overview
        $.plot($("#overviewTransactionsPerSecond"), dataset, prepareOverviewOptions(options));
    }
};

// Transactions per second
function refreshTransactionsPerSecond(fixTimestamps) {
    var infos = transactionsPerSecondInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyTransactionsPerSecond");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotTransactionsPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTransactionsPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTransactionsPerSecond", "#overviewTransactionsPerSecond");
        $('#footerTransactionsPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var totalTPSInfos = {
        data: {"result": {"minY": 3.3333333333333335, "minX": 1.7754063E12, "maxY": 3.3333333333333335, "series": [{"data": [[1.7754063E12, 3.3333333333333335]], "isOverall": false, "label": "Transaction-success", "isController": false}, {"data": [], "isOverall": false, "label": "Transaction-failure", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.7754063E12, "title": "Total Transactions Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of transactions / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendTotalTPS"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y transactions / sec"
                },
                colors: ["#9ACD32", "#FF6347"]
            };
        },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesTotalTPS"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotTotalTPS"), dataset, options);
        // setup overview
        $.plot($("#overviewTotalTPS"), dataset, prepareOverviewOptions(options));
    }
};

// Total Transactions per second
function refreshTotalTPS(fixTimestamps) {
    var infos = totalTPSInfos;
    // We want to ignore seriesFilter
    prepareSeries(infos.data, false, true);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotTotalTPS"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTotalTPS");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTotalTPS", "#overviewTotalTPS");
        $('#footerTotalTPS .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

// Collapse the graph matching the specified DOM element depending the collapsed
// status
function collapse(elem, collapsed){
    if(collapsed){
        $(elem).parent().find(".fa-chevron-up").removeClass("fa-chevron-up").addClass("fa-chevron-down");
    } else {
        $(elem).parent().find(".fa-chevron-down").removeClass("fa-chevron-down").addClass("fa-chevron-up");
        if (elem.id == "bodyBytesThroughputOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshBytesThroughputOverTime(true);
            }
            document.location.href="#bytesThroughputOverTime";
        } else if (elem.id == "bodyLatenciesOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshLatenciesOverTime(true);
            }
            document.location.href="#latenciesOverTime";
        } else if (elem.id == "bodyCustomGraph") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshCustomGraph(true);
            }
            document.location.href="#responseCustomGraph";
        } else if (elem.id == "bodyConnectTimeOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshConnectTimeOverTime(true);
            }
            document.location.href="#connectTimeOverTime";
        } else if (elem.id == "bodyResponseTimePercentilesOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimePercentilesOverTime(true);
            }
            document.location.href="#responseTimePercentilesOverTime";
        } else if (elem.id == "bodyResponseTimeDistribution") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimeDistribution();
            }
            document.location.href="#responseTimeDistribution" ;
        } else if (elem.id == "bodySyntheticResponseTimeDistribution") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshSyntheticResponseTimeDistribution();
            }
            document.location.href="#syntheticResponseTimeDistribution" ;
        } else if (elem.id == "bodyActiveThreadsOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshActiveThreadsOverTime(true);
            }
            document.location.href="#activeThreadsOverTime";
        } else if (elem.id == "bodyTimeVsThreads") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTimeVsThreads();
            }
            document.location.href="#timeVsThreads" ;
        } else if (elem.id == "bodyCodesPerSecond") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshCodesPerSecond(true);
            }
            document.location.href="#codesPerSecond";
        } else if (elem.id == "bodyTransactionsPerSecond") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTransactionsPerSecond(true);
            }
            document.location.href="#transactionsPerSecond";
        } else if (elem.id == "bodyTotalTPS") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTotalTPS(true);
            }
            document.location.href="#totalTPS";
        } else if (elem.id == "bodyResponseTimeVsRequest") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimeVsRequest();
            }
            document.location.href="#responseTimeVsRequest";
        } else if (elem.id == "bodyLatenciesVsRequest") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshLatenciesVsRequest();
            }
            document.location.href="#latencyVsRequest";
        }
    }
}

/*
 * Activates or deactivates all series of the specified graph (represented by id parameter)
 * depending on checked argument.
 */
function toggleAll(id, checked){
    var placeholder = document.getElementById(id);

    var cases = $(placeholder).find(':checkbox');
    cases.prop('checked', checked);
    $(cases).parent().children().children().toggleClass("legend-disabled", !checked);

    var choiceContainer;
    if ( id == "choicesBytesThroughputOverTime"){
        choiceContainer = $("#choicesBytesThroughputOverTime");
        refreshBytesThroughputOverTime(false);
    } else if(id == "choicesResponseTimesOverTime"){
        choiceContainer = $("#choicesResponseTimesOverTime");
        refreshResponseTimeOverTime(false);
    }else if(id == "choicesResponseCustomGraph"){
        choiceContainer = $("#choicesResponseCustomGraph");
        refreshCustomGraph(false);
    } else if ( id == "choicesLatenciesOverTime"){
        choiceContainer = $("#choicesLatenciesOverTime");
        refreshLatenciesOverTime(false);
    } else if ( id == "choicesConnectTimeOverTime"){
        choiceContainer = $("#choicesConnectTimeOverTime");
        refreshConnectTimeOverTime(false);
    } else if ( id == "choicesResponseTimePercentilesOverTime"){
        choiceContainer = $("#choicesResponseTimePercentilesOverTime");
        refreshResponseTimePercentilesOverTime(false);
    } else if ( id == "choicesResponseTimePercentiles"){
        choiceContainer = $("#choicesResponseTimePercentiles");
        refreshResponseTimePercentiles();
    } else if(id == "choicesActiveThreadsOverTime"){
        choiceContainer = $("#choicesActiveThreadsOverTime");
        refreshActiveThreadsOverTime(false);
    } else if ( id == "choicesTimeVsThreads"){
        choiceContainer = $("#choicesTimeVsThreads");
        refreshTimeVsThreads();
    } else if ( id == "choicesSyntheticResponseTimeDistribution"){
        choiceContainer = $("#choicesSyntheticResponseTimeDistribution");
        refreshSyntheticResponseTimeDistribution();
    } else if ( id == "choicesResponseTimeDistribution"){
        choiceContainer = $("#choicesResponseTimeDistribution");
        refreshResponseTimeDistribution();
    } else if ( id == "choicesHitsPerSecond"){
        choiceContainer = $("#choicesHitsPerSecond");
        refreshHitsPerSecond(false);
    } else if(id == "choicesCodesPerSecond"){
        choiceContainer = $("#choicesCodesPerSecond");
        refreshCodesPerSecond(false);
    } else if ( id == "choicesTransactionsPerSecond"){
        choiceContainer = $("#choicesTransactionsPerSecond");
        refreshTransactionsPerSecond(false);
    } else if ( id == "choicesTotalTPS"){
        choiceContainer = $("#choicesTotalTPS");
        refreshTotalTPS(false);
    } else if ( id == "choicesResponseTimeVsRequest"){
        choiceContainer = $("#choicesResponseTimeVsRequest");
        refreshResponseTimeVsRequest();
    } else if ( id == "choicesLatencyVsRequest"){
        choiceContainer = $("#choicesLatencyVsRequest");
        refreshLatenciesVsRequest();
    }
    var color = checked ? "black" : "#818181";
    if(choiceContainer != null) {
        choiceContainer.find("label").each(function(){
            this.style.color = color;
        });
    }
}

