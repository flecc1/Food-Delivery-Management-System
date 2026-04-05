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
        data: {"result": {"minY": 0.0, "minX": 0.0, "maxY": 1993.0, "series": [{"data": [[0.0, 0.0], [0.1, 0.0], [0.2, 0.0], [0.3, 0.0], [0.4, 0.0], [0.5, 0.0], [0.6, 0.0], [0.7, 0.0], [0.8, 0.0], [0.9, 0.0], [1.0, 0.0], [1.1, 0.0], [1.2, 0.0], [1.3, 0.0], [1.4, 0.0], [1.5, 0.0], [1.6, 0.0], [1.7, 0.0], [1.8, 0.0], [1.9, 0.0], [2.0, 0.0], [2.1, 0.0], [2.2, 0.0], [2.3, 0.0], [2.4, 0.0], [2.5, 0.0], [2.6, 0.0], [2.7, 0.0], [2.8, 0.0], [2.9, 0.0], [3.0, 0.0], [3.1, 0.0], [3.2, 0.0], [3.3, 0.0], [3.4, 0.0], [3.5, 0.0], [3.6, 0.0], [3.7, 0.0], [3.8, 0.0], [3.9, 0.0], [4.0, 0.0], [4.1, 0.0], [4.2, 0.0], [4.3, 0.0], [4.4, 0.0], [4.5, 1.0], [4.6, 1.0], [4.7, 1.0], [4.8, 1.0], [4.9, 1.0], [5.0, 1.0], [5.1, 1.0], [5.2, 1.0], [5.3, 1.0], [5.4, 1.0], [5.5, 1.0], [5.6, 1.0], [5.7, 1.0], [5.8, 1.0], [5.9, 1.0], [6.0, 1.0], [6.1, 1.0], [6.2, 1.0], [6.3, 1.0], [6.4, 1.0], [6.5, 1.0], [6.6, 1.0], [6.7, 1.0], [6.8, 1.0], [6.9, 1.0], [7.0, 1.0], [7.1, 1.0], [7.2, 1.0], [7.3, 1.0], [7.4, 1.0], [7.5, 1.0], [7.6, 1.0], [7.7, 1.0], [7.8, 1.0], [7.9, 1.0], [8.0, 1.0], [8.1, 1.0], [8.2, 1.0], [8.3, 1.0], [8.4, 1.0], [8.5, 1.0], [8.6, 1.0], [8.7, 1.0], [8.8, 1.0], [8.9, 1.0], [9.0, 1.0], [9.1, 1.0], [9.2, 1.0], [9.3, 1.0], [9.4, 1.0], [9.5, 1.0], [9.6, 1.0], [9.7, 1.0], [9.8, 1.0], [9.9, 1.0], [10.0, 1.0], [10.1, 1.0], [10.2, 1.0], [10.3, 1.0], [10.4, 1.0], [10.5, 1.0], [10.6, 1.0], [10.7, 1.0], [10.8, 1.0], [10.9, 1.0], [11.0, 1.0], [11.1, 1.0], [11.2, 1.0], [11.3, 1.0], [11.4, 1.0], [11.5, 1.0], [11.6, 1.0], [11.7, 1.0], [11.8, 1.0], [11.9, 1.0], [12.0, 1.0], [12.1, 1.0], [12.2, 1.0], [12.3, 1.0], [12.4, 1.0], [12.5, 1.0], [12.6, 1.0], [12.7, 1.0], [12.8, 1.0], [12.9, 1.0], [13.0, 1.0], [13.1, 1.0], [13.2, 1.0], [13.3, 1.0], [13.4, 1.0], [13.5, 1.0], [13.6, 1.0], [13.7, 1.0], [13.8, 1.0], [13.9, 1.0], [14.0, 1.0], [14.1, 1.0], [14.2, 1.0], [14.3, 1.0], [14.4, 1.0], [14.5, 1.0], [14.6, 1.0], [14.7, 1.0], [14.8, 1.0], [14.9, 1.0], [15.0, 1.0], [15.1, 1.0], [15.2, 1.0], [15.3, 1.0], [15.4, 1.0], [15.5, 1.0], [15.6, 1.0], [15.7, 1.0], [15.8, 1.0], [15.9, 1.0], [16.0, 1.0], [16.1, 1.0], [16.2, 1.0], [16.3, 1.0], [16.4, 1.0], [16.5, 1.0], [16.6, 1.0], [16.7, 1.0], [16.8, 1.0], [16.9, 1.0], [17.0, 1.0], [17.1, 1.0], [17.2, 1.0], [17.3, 1.0], [17.4, 1.0], [17.5, 1.0], [17.6, 1.0], [17.7, 1.0], [17.8, 1.0], [17.9, 1.0], [18.0, 1.0], [18.1, 1.0], [18.2, 1.0], [18.3, 1.0], [18.4, 1.0], [18.5, 1.0], [18.6, 1.0], [18.7, 1.0], [18.8, 1.0], [18.9, 1.0], [19.0, 1.0], [19.1, 1.0], [19.2, 2.0], [19.3, 2.0], [19.4, 2.0], [19.5, 2.0], [19.6, 2.0], [19.7, 2.0], [19.8, 2.0], [19.9, 2.0], [20.0, 2.0], [20.1, 2.0], [20.2, 2.0], [20.3, 2.0], [20.4, 2.0], [20.5, 2.0], [20.6, 2.0], [20.7, 2.0], [20.8, 2.0], [20.9, 2.0], [21.0, 2.0], [21.1, 2.0], [21.2, 2.0], [21.3, 2.0], [21.4, 2.0], [21.5, 2.0], [21.6, 2.0], [21.7, 2.0], [21.8, 2.0], [21.9, 2.0], [22.0, 2.0], [22.1, 2.0], [22.2, 2.0], [22.3, 2.0], [22.4, 2.0], [22.5, 3.0], [22.6, 3.0], [22.7, 3.0], [22.8, 3.0], [22.9, 3.0], [23.0, 3.0], [23.1, 3.0], [23.2, 3.0], [23.3, 3.0], [23.4, 3.0], [23.5, 3.0], [23.6, 3.0], [23.7, 4.0], [23.8, 4.0], [23.9, 4.0], [24.0, 4.0], [24.1, 4.0], [24.2, 4.0], [24.3, 4.0], [24.4, 4.0], [24.5, 4.0], [24.6, 4.0], [24.7, 4.0], [24.8, 4.0], [24.9, 4.0], [25.0, 5.0], [25.1, 5.0], [25.2, 5.0], [25.3, 5.0], [25.4, 5.0], [25.5, 5.0], [25.6, 5.0], [25.7, 5.0], [25.8, 5.0], [25.9, 5.0], [26.0, 5.0], [26.1, 5.0], [26.2, 5.0], [26.3, 5.0], [26.4, 5.0], [26.5, 5.0], [26.6, 5.0], [26.7, 5.0], [26.8, 5.0], [26.9, 5.0], [27.0, 6.0], [27.1, 6.0], [27.2, 6.0], [27.3, 6.0], [27.4, 6.0], [27.5, 6.0], [27.6, 6.0], [27.7, 7.0], [27.8, 7.0], [27.9, 7.0], [28.0, 8.0], [28.1, 8.0], [28.2, 8.0], [28.3, 8.0], [28.4, 8.0], [28.5, 8.0], [28.6, 8.0], [28.7, 8.0], [28.8, 8.0], [28.9, 8.0], [29.0, 10.0], [29.1, 10.0], [29.2, 10.0], [29.3, 10.0], [29.4, 10.0], [29.5, 11.0], [29.6, 11.0], [29.7, 12.0], [29.8, 12.0], [29.9, 14.0], [30.0, 15.0], [30.1, 15.0], [30.2, 15.0], [30.3, 15.0], [30.4, 15.0], [30.5, 17.0], [30.6, 17.0], [30.7, 17.0], [30.8, 17.0], [30.9, 18.0], [31.0, 18.0], [31.1, 18.0], [31.2, 19.0], [31.3, 19.0], [31.4, 19.0], [31.5, 22.0], [31.6, 22.0], [31.7, 22.0], [31.8, 22.0], [31.9, 24.0], [32.0, 24.0], [32.1, 24.0], [32.2, 25.0], [32.3, 25.0], [32.4, 27.0], [32.5, 28.0], [32.6, 28.0], [32.7, 29.0], [32.8, 29.0], [32.9, 29.0], [33.0, 30.0], [33.1, 30.0], [33.2, 30.0], [33.3, 30.0], [33.4, 31.0], [33.5, 31.0], [33.6, 31.0], [33.7, 32.0], [33.8, 32.0], [33.9, 32.0], [34.0, 33.0], [34.1, 33.0], [34.2, 33.0], [34.3, 33.0], [34.4, 34.0], [34.5, 34.0], [34.6, 34.0], [34.7, 34.0], [34.8, 34.0], [34.9, 34.0], [35.0, 34.0], [35.1, 34.0], [35.2, 34.0], [35.3, 34.0], [35.4, 35.0], [35.5, 35.0], [35.6, 35.0], [35.7, 35.0], [35.8, 35.0], [35.9, 36.0], [36.0, 36.0], [36.1, 36.0], [36.2, 37.0], [36.3, 37.0], [36.4, 37.0], [36.5, 37.0], [36.6, 37.0], [36.7, 38.0], [36.8, 38.0], [36.9, 38.0], [37.0, 38.0], [37.1, 38.0], [37.2, 39.0], [37.3, 39.0], [37.4, 39.0], [37.5, 39.0], [37.6, 39.0], [37.7, 41.0], [37.8, 41.0], [37.9, 42.0], [38.0, 43.0], [38.1, 43.0], [38.2, 43.0], [38.3, 43.0], [38.4, 43.0], [38.5, 44.0], [38.6, 44.0], [38.7, 47.0], [38.8, 47.0], [38.9, 47.0], [39.0, 47.0], [39.1, 47.0], [39.2, 48.0], [39.3, 48.0], [39.4, 49.0], [39.5, 49.0], [39.6, 49.0], [39.7, 49.0], [39.8, 49.0], [39.9, 49.0], [40.0, 49.0], [40.1, 49.0], [40.2, 50.0], [40.3, 50.0], [40.4, 50.0], [40.5, 51.0], [40.6, 51.0], [40.7, 51.0], [40.8, 51.0], [40.9, 52.0], [41.0, 52.0], [41.1, 52.0], [41.2, 53.0], [41.3, 53.0], [41.4, 54.0], [41.5, 55.0], [41.6, 55.0], [41.7, 56.0], [41.8, 56.0], [41.9, 56.0], [42.0, 56.0], [42.1, 56.0], [42.2, 58.0], [42.3, 58.0], [42.4, 58.0], [42.5, 59.0], [42.6, 59.0], [42.7, 59.0], [42.8, 59.0], [42.9, 60.0], [43.0, 61.0], [43.1, 61.0], [43.2, 61.0], [43.3, 61.0], [43.4, 62.0], [43.5, 66.0], [43.6, 66.0], [43.7, 66.0], [43.8, 66.0], [43.9, 66.0], [44.0, 68.0], [44.1, 68.0], [44.2, 69.0], [44.3, 69.0], [44.4, 71.0], [44.5, 72.0], [44.6, 72.0], [44.7, 72.0], [44.8, 72.0], [44.9, 76.0], [45.0, 78.0], [45.1, 78.0], [45.2, 82.0], [45.3, 82.0], [45.4, 87.0], [45.5, 89.0], [45.6, 89.0], [45.7, 90.0], [45.8, 90.0], [45.9, 94.0], [46.0, 96.0], [46.1, 96.0], [46.2, 99.0], [46.3, 99.0], [46.4, 101.0], [46.5, 105.0], [46.6, 105.0], [46.7, 109.0], [46.8, 109.0], [46.9, 111.0], [47.0, 112.0], [47.1, 112.0], [47.2, 113.0], [47.3, 113.0], [47.4, 116.0], [47.5, 117.0], [47.6, 117.0], [47.7, 117.0], [47.8, 117.0], [47.9, 117.0], [48.0, 118.0], [48.1, 118.0], [48.2, 118.0], [48.3, 118.0], [48.4, 119.0], [48.5, 119.0], [48.6, 119.0], [48.7, 119.0], [48.8, 119.0], [48.9, 122.0], [49.0, 123.0], [49.1, 123.0], [49.2, 123.0], [49.3, 123.0], [49.4, 125.0], [49.5, 126.0], [49.6, 126.0], [49.7, 133.0], [49.8, 133.0], [49.9, 133.0], [50.0, 134.0], [50.1, 134.0], [50.2, 140.0], [50.3, 140.0], [50.4, 140.0], [50.5, 143.0], [50.6, 143.0], [50.7, 144.0], [50.8, 144.0], [50.9, 146.0], [51.0, 147.0], [51.1, 147.0], [51.2, 151.0], [51.3, 151.0], [51.4, 154.0], [51.5, 155.0], [51.6, 155.0], [51.7, 155.0], [51.8, 155.0], [51.9, 156.0], [52.0, 160.0], [52.1, 160.0], [52.2, 160.0], [52.3, 160.0], [52.4, 162.0], [52.5, 163.0], [52.6, 163.0], [52.7, 164.0], [52.8, 164.0], [52.9, 167.0], [53.0, 172.0], [53.1, 172.0], [53.2, 174.0], [53.3, 174.0], [53.4, 174.0], [53.5, 176.0], [53.6, 176.0], [53.7, 177.0], [53.8, 177.0], [53.9, 182.0], [54.0, 183.0], [54.1, 183.0], [54.2, 183.0], [54.3, 183.0], [54.4, 186.0], [54.5, 187.0], [54.6, 187.0], [54.7, 191.0], [54.8, 191.0], [54.9, 191.0], [55.0, 196.0], [55.1, 196.0], [55.2, 196.0], [55.3, 196.0], [55.4, 197.0], [55.5, 197.0], [55.6, 197.0], [55.7, 208.0], [55.8, 208.0], [55.9, 211.0], [56.0, 213.0], [56.1, 213.0], [56.2, 214.0], [56.3, 214.0], [56.4, 217.0], [56.5, 222.0], [56.6, 222.0], [56.7, 225.0], [56.8, 225.0], [56.9, 226.0], [57.0, 231.0], [57.1, 231.0], [57.2, 235.0], [57.3, 235.0], [57.4, 238.0], [57.5, 241.0], [57.6, 241.0], [57.7, 243.0], [57.8, 243.0], [57.9, 245.0], [58.0, 245.0], [58.1, 245.0], [58.2, 245.0], [58.3, 245.0], [58.4, 251.0], [58.5, 256.0], [58.6, 256.0], [58.7, 259.0], [58.8, 259.0], [58.9, 263.0], [59.0, 266.0], [59.1, 266.0], [59.2, 269.0], [59.3, 269.0], [59.4, 269.0], [59.5, 276.0], [59.6, 276.0], [59.7, 276.0], [59.8, 276.0], [59.9, 287.0], [60.0, 287.0], [60.1, 287.0], [60.2, 289.0], [60.3, 289.0], [60.4, 290.0], [60.5, 292.0], [60.6, 292.0], [60.7, 292.0], [60.8, 292.0], [60.9, 301.0], [61.0, 302.0], [61.1, 302.0], [61.2, 302.0], [61.3, 302.0], [61.4, 302.0], [61.5, 307.0], [61.6, 307.0], [61.7, 310.0], [61.8, 310.0], [61.9, 313.0], [62.0, 318.0], [62.1, 318.0], [62.2, 321.0], [62.3, 321.0], [62.4, 326.0], [62.5, 334.0], [62.6, 334.0], [62.7, 340.0], [62.8, 340.0], [62.9, 344.0], [63.0, 348.0], [63.1, 348.0], [63.2, 352.0], [63.3, 352.0], [63.4, 354.0], [63.5, 354.0], [63.6, 354.0], [63.7, 355.0], [63.8, 355.0], [63.9, 355.0], [64.0, 361.0], [64.1, 361.0], [64.2, 363.0], [64.3, 363.0], [64.4, 363.0], [64.5, 364.0], [64.6, 364.0], [64.7, 364.0], [64.8, 364.0], [64.9, 365.0], [65.0, 366.0], [65.1, 366.0], [65.2, 370.0], [65.3, 370.0], [65.4, 380.0], [65.5, 383.0], [65.6, 383.0], [65.7, 387.0], [65.8, 387.0], [65.9, 404.0], [66.0, 404.0], [66.1, 404.0], [66.2, 416.0], [66.3, 416.0], [66.4, 422.0], [66.5, 424.0], [66.6, 424.0], [66.7, 425.0], [66.8, 425.0], [66.9, 425.0], [67.0, 428.0], [67.1, 428.0], [67.2, 428.0], [67.3, 428.0], [67.4, 432.0], [67.5, 433.0], [67.6, 433.0], [67.7, 433.0], [67.8, 433.0], [67.9, 437.0], [68.0, 440.0], [68.1, 440.0], [68.2, 441.0], [68.3, 441.0], [68.4, 441.0], [68.5, 442.0], [68.6, 442.0], [68.7, 442.0], [68.8, 442.0], [68.9, 442.0], [69.0, 445.0], [69.1, 445.0], [69.2, 447.0], [69.3, 447.0], [69.4, 451.0], [69.5, 457.0], [69.6, 457.0], [69.7, 461.0], [69.8, 461.0], [69.9, 464.0], [70.0, 469.0], [70.1, 469.0], [70.2, 470.0], [70.3, 470.0], [70.4, 472.0], [70.5, 477.0], [70.6, 477.0], [70.7, 488.0], [70.8, 488.0], [70.9, 491.0], [71.0, 495.0], [71.1, 495.0], [71.2, 496.0], [71.3, 496.0], [71.4, 497.0], [71.5, 498.0], [71.6, 498.0], [71.7, 500.0], [71.8, 500.0], [71.9, 501.0], [72.0, 502.0], [72.1, 502.0], [72.2, 502.0], [72.3, 502.0], [72.4, 508.0], [72.5, 512.0], [72.6, 512.0], [72.7, 517.0], [72.8, 517.0], [72.9, 518.0], [73.0, 519.0], [73.1, 519.0], [73.2, 520.0], [73.3, 520.0], [73.4, 521.0], [73.5, 521.0], [73.6, 522.0], [73.7, 522.0], [73.8, 522.0], [73.9, 522.0], [74.0, 522.0], [74.1, 522.0], [74.2, 528.0], [74.3, 528.0], [74.4, 537.0], [74.5, 537.0], [74.6, 540.0], [74.7, 544.0], [74.8, 544.0], [74.9, 547.0], [75.0, 547.0], [75.1, 549.0], [75.2, 557.0], [75.3, 557.0], [75.4, 567.0], [75.5, 567.0], [75.6, 577.0], [75.7, 580.0], [75.8, 580.0], [75.9, 582.0], [76.0, 582.0], [76.1, 583.0], [76.2, 594.0], [76.3, 594.0], [76.4, 595.0], [76.5, 595.0], [76.6, 600.0], [76.7, 603.0], [76.8, 603.0], [76.9, 603.0], [77.0, 603.0], [77.1, 613.0], [77.2, 621.0], [77.3, 621.0], [77.4, 624.0], [77.5, 624.0], [77.6, 642.0], [77.7, 663.0], [77.8, 663.0], [77.9, 670.0], [78.0, 670.0], [78.1, 677.0], [78.2, 679.0], [78.3, 679.0], [78.4, 685.0], [78.5, 685.0], [78.6, 688.0], [78.7, 700.0], [78.8, 700.0], [78.9, 704.0], [79.0, 704.0], [79.1, 707.0], [79.2, 715.0], [79.3, 715.0], [79.4, 730.0], [79.5, 730.0], [79.6, 738.0], [79.7, 740.0], [79.8, 740.0], [79.9, 742.0], [80.0, 742.0], [80.1, 758.0], [80.2, 765.0], [80.3, 765.0], [80.4, 766.0], [80.5, 766.0], [80.6, 785.0], [80.7, 790.0], [80.8, 790.0], [80.9, 799.0], [81.0, 799.0], [81.1, 810.0], [81.2, 817.0], [81.3, 817.0], [81.4, 817.0], [81.5, 817.0], [81.6, 825.0], [81.7, 829.0], [81.8, 829.0], [81.9, 831.0], [82.0, 831.0], [82.1, 850.0], [82.2, 859.0], [82.3, 859.0], [82.4, 861.0], [82.5, 861.0], [82.6, 866.0], [82.7, 876.0], [82.8, 876.0], [82.9, 885.0], [83.0, 885.0], [83.1, 917.0], [83.2, 919.0], [83.3, 919.0], [83.4, 929.0], [83.5, 929.0], [83.6, 943.0], [83.7, 958.0], [83.8, 958.0], [83.9, 976.0], [84.0, 976.0], [84.1, 992.0], [84.2, 1001.0], [84.3, 1001.0], [84.4, 1001.0], [84.5, 1001.0], [84.6, 1022.0], [84.7, 1034.0], [84.8, 1034.0], [84.9, 1045.0], [85.0, 1045.0], [85.1, 1055.0], [85.2, 1057.0], [85.3, 1057.0], [85.4, 1077.0], [85.5, 1077.0], [85.6, 1078.0], [85.7, 1086.0], [85.8, 1086.0], [85.9, 1086.0], [86.0, 1086.0], [86.1, 1106.0], [86.2, 1120.0], [86.3, 1120.0], [86.4, 1133.0], [86.5, 1133.0], [86.6, 1144.0], [86.7, 1154.0], [86.8, 1154.0], [86.9, 1155.0], [87.0, 1155.0], [87.1, 1160.0], [87.2, 1179.0], [87.3, 1179.0], [87.4, 1184.0], [87.5, 1184.0], [87.6, 1191.0], [87.7, 1222.0], [87.8, 1222.0], [87.9, 1239.0], [88.0, 1239.0], [88.1, 1253.0], [88.2, 1256.0], [88.3, 1256.0], [88.4, 1267.0], [88.5, 1267.0], [88.6, 1274.0], [88.7, 1291.0], [88.8, 1291.0], [88.9, 1299.0], [89.0, 1299.0], [89.1, 1322.0], [89.2, 1353.0], [89.3, 1353.0], [89.4, 1355.0], [89.5, 1355.0], [89.6, 1362.0], [89.7, 1364.0], [89.8, 1364.0], [89.9, 1365.0], [90.0, 1365.0], [90.1, 1368.0], [90.2, 1373.0], [90.3, 1373.0], [90.4, 1387.0], [90.5, 1387.0], [90.6, 1394.0], [90.7, 1397.0], [90.8, 1397.0], [90.9, 1401.0], [91.0, 1401.0], [91.1, 1406.0], [91.2, 1424.0], [91.3, 1424.0], [91.4, 1425.0], [91.5, 1425.0], [91.6, 1435.0], [91.7, 1452.0], [91.8, 1452.0], [91.9, 1474.0], [92.0, 1474.0], [92.1, 1474.0], [92.2, 1475.0], [92.3, 1475.0], [92.4, 1476.0], [92.5, 1476.0], [92.6, 1477.0], [92.7, 1480.0], [92.8, 1480.0], [92.9, 1489.0], [93.0, 1489.0], [93.1, 1502.0], [93.2, 1502.0], [93.3, 1502.0], [93.4, 1503.0], [93.5, 1503.0], [93.6, 1508.0], [93.7, 1516.0], [93.8, 1516.0], [93.9, 1519.0], [94.0, 1519.0], [94.1, 1547.0], [94.2, 1559.0], [94.3, 1559.0], [94.4, 1560.0], [94.5, 1560.0], [94.6, 1564.0], [94.7, 1599.0], [94.8, 1599.0], [94.9, 1600.0], [95.0, 1600.0], [95.1, 1602.0], [95.2, 1618.0], [95.3, 1618.0], [95.4, 1635.0], [95.5, 1635.0], [95.6, 1637.0], [95.7, 1653.0], [95.8, 1653.0], [95.9, 1658.0], [96.0, 1658.0], [96.1, 1658.0], [96.2, 1671.0], [96.3, 1671.0], [96.4, 1683.0], [96.5, 1683.0], [96.6, 1693.0], [96.7, 1697.0], [96.8, 1697.0], [96.9, 1701.0], [97.0, 1701.0], [97.1, 1730.0], [97.2, 1751.0], [97.3, 1751.0], [97.4, 1755.0], [97.5, 1755.0], [97.6, 1757.0], [97.7, 1785.0], [97.8, 1785.0], [97.9, 1789.0], [98.0, 1789.0], [98.1, 1796.0], [98.2, 1800.0], [98.3, 1800.0], [98.4, 1813.0], [98.5, 1813.0], [98.6, 1818.0], [98.7, 1831.0], [98.8, 1831.0], [98.9, 1834.0], [99.0, 1834.0], [99.1, 1852.0], [99.2, 1858.0], [99.3, 1858.0], [99.4, 1859.0], [99.5, 1859.0], [99.6, 1931.0], [99.7, 1966.0], [99.8, 1966.0], [99.9, 1993.0], [100.0, 1993.0]], "isOverall": false, "label": "HTTP Request", "isController": false}], "supportsControllersDiscrimination": true, "maxX": 100.0, "title": "Response Time Percentiles"}},
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
        data: {"result": {"minY": 3.0, "minX": 0.0, "maxY": 278.0, "series": [{"data": [[0.0, 278.0], [600.0, 13.0], [700.0, 14.0], [200.0, 31.0], [800.0, 12.0], [900.0, 7.0], [1000.0, 11.0], [1100.0, 10.0], [300.0, 30.0], [1200.0, 8.0], [1300.0, 11.0], [1400.0, 13.0], [1500.0, 11.0], [100.0, 56.0], [400.0, 35.0], [1600.0, 12.0], [1700.0, 8.0], [1800.0, 8.0], [1900.0, 3.0], [500.0, 29.0]], "isOverall": false, "label": "HTTP Request", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 100, "maxX": 1900.0, "title": "Response Time Distribution"}},
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
        data: {"result": {"minY": 42.0, "minX": 0.0, "ticks": [[0, "Requests having \nresponse time <= 500ms"], [1, "Requests having \nresponse time > 500ms and <= 1,500ms"], [2, "Requests having \nresponse time > 1,500ms"], [3, "Requests in error"]], "maxY": 381.0, "series": [{"data": [[0.0, 381.0]], "color": "#9ACD32", "isOverall": false, "label": "Requests having \nresponse time <= 500ms", "isController": false}, {"data": [[1.0, 127.0]], "color": "yellow", "isOverall": false, "label": "Requests having \nresponse time > 500ms and <= 1,500ms", "isController": false}, {"data": [[2.0, 42.0]], "color": "orange", "isOverall": false, "label": "Requests having \nresponse time > 1,500ms", "isController": false}, {"data": [[3.0, 50.0]], "color": "#FF6347", "isOverall": false, "label": "Requests in error", "isController": false}], "supportsControllersDiscrimination": false, "maxX": 3.0, "title": "Synthetic Response Times Distribution"}},
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
        data: {"result": {"minY": 81.85666666666668, "minX": 1.77540714E12, "maxY": 81.85666666666668, "series": [{"data": [[1.77540714E12, 81.85666666666668]], "isOverall": false, "label": "users", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.77540714E12, "title": "Active Threads Over Time"}},
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
        data: {"result": {"minY": 0.0, "minX": 1.0, "maxY": 924.0, "series": [{"data": [[2.0, 620.0], [3.0, 897.0], [4.0, 1.0], [5.0, 721.0], [6.0, 1.0], [7.0, 680.8], [9.0, 552.5], [10.0, 611.0], [11.0, 600.6666666666667], [12.0, 566.0], [13.0, 2.5], [14.0, 658.3333333333333], [15.0, 907.25], [17.0, 606.0], [18.0, 2.0], [19.0, 739.0], [20.0, 586.3333333333333], [21.0, 553.3333333333333], [22.0, 0.0], [23.0, 538.3333333333333], [24.0, 362.2], [25.0, 453.49999999999994], [26.0, 522.0], [27.0, 389.74999999999994], [28.0, 17.0], [29.0, 23.6], [30.0, 2.0], [31.0, 924.0], [32.0, 357.20000000000005], [33.0, 1.0], [34.0, 573.0], [35.0, 430.0], [36.0, 318.5], [37.0, 401.6], [39.0, 186.66666666666666], [38.0, 455.33333333333337], [41.0, 62.95652173913043], [40.0, 285.2], [43.0, 79.0], [42.0, 161.4], [44.0, 752.0], [46.0, 348.625], [47.0, 409.25000000000006], [48.0, 122.0], [51.0, 52.5], [50.0, 21.5], [53.0, 504.1666666666667], [54.0, 85.2], [55.0, 872.0000000000001], [57.0, 366.6], [56.0, 562.6666666666667], [58.0, 297.6], [59.0, 7.0], [61.0, 14.5], [60.0, 595.75], [62.0, 110.0], [63.0, 752.75], [65.0, 429.75000000000006], [66.0, 410.25], [64.0, 38.0], [69.0, 345.6], [71.0, 261.75], [70.0, 788.8], [68.0, 643.2499999999999], [73.0, 143.4], [74.0, 76.66666666666666], [75.0, 29.5], [77.0, 540.25], [78.0, 192.5], [79.0, 329.75], [76.0, 9.0], [82.0, 211.8], [81.0, 495.0], [80.0, 14.5], [87.0, 886.142857142857], [86.0, 263.6], [84.0, 429.83333333333337], [88.0, 259.0], [90.0, 354.2857142857143], [92.0, 127.8], [93.0, 440.0], [94.0, 208.16666666666669], [95.0, 103.75000000000001], [97.0, 383.0], [98.0, 140.0], [99.0, 363.0], [101.0, 59.0], [103.0, 112.75000000000001], [105.0, 93.75], [106.0, 93.75], [107.0, 72.16666666666666], [104.0, 4.0], [111.0, 184.75], [109.0, 5.0], [114.0, 204.2], [115.0, 404.0], [112.0, 5.6], [117.0, 502.0], [118.0, 160.66666666666669], [119.0, 428.0], [116.0, 285.8], [123.0, 306.0], [121.0, 550.0], [120.0, 7.0], [125.0, 519.0], [127.0, 115.0], [126.0, 34.0], [124.0, 442.0], [128.0, 213.6], [131.0, 539.0], [135.0, 59.75], [134.0, 117.5], [132.0, 160.2], [130.0, 86.0], [140.0, 423.0], [143.0, 350.3333333333333], [142.0, 328.0], [139.0, 302.0], [138.0, 283.25], [137.0, 404.0], [136.0, 175.0], [145.0, 336.25], [146.0, 728.9473684210526], [151.0, 571.5], [150.0, 468.14285714285717], [149.0, 635.25], [147.0, 878.7500000000001], [144.0, 354.0], [152.0, 605.5454545454545], [156.0, 348.0], [154.0, 325.75], [158.0, 280.3333333333333], [157.0, 433.0], [159.0, 502.5], [155.0, 584.75], [153.0, 630.5], [160.0, 611.3333333333334], [164.0, 588.3333333333334], [166.0, 601.2], [167.0, 523.6666666666666], [161.0, 704.4444444444445], [168.0, 285.5], [1.0, 1.0]], "isOverall": false, "label": "HTTP Request", "isController": false}, {"data": [[81.85666666666668, 394.2883333333337]], "isOverall": false, "label": "HTTP Request-Aggregated", "isController": false}], "supportsControllersDiscrimination": true, "maxX": 168.0, "title": "Time VS Threads"}},
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
        data : {"result": {"minY": 1820.0, "minX": 1.77540714E12, "maxY": 2509.8333333333335, "series": [{"data": [[1.77540714E12, 2509.8333333333335]], "isOverall": false, "label": "Bytes received per second", "isController": false}, {"data": [[1.77540714E12, 1820.0]], "isOverall": false, "label": "Bytes sent per second", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.77540714E12, "title": "Bytes Throughput Over Time"}},
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
        data: {"result": {"minY": 394.2883333333337, "minX": 1.77540714E12, "maxY": 394.2883333333337, "series": [{"data": [[1.77540714E12, 394.2883333333337]], "isOverall": false, "label": "HTTP Request", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.77540714E12, "title": "Response Time Over Time"}},
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
        data: {"result": {"minY": 394.2566666666664, "minX": 1.77540714E12, "maxY": 394.2566666666664, "series": [{"data": [[1.77540714E12, 394.2566666666664]], "isOverall": false, "label": "HTTP Request", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.77540714E12, "title": "Latencies Over Time"}},
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
        data: {"result": {"minY": 0.44166666666666693, "minX": 1.77540714E12, "maxY": 0.44166666666666693, "series": [{"data": [[1.77540714E12, 0.44166666666666693]], "isOverall": false, "label": "HTTP Request", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.77540714E12, "title": "Connect Time Over Time"}},
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
        data: {"result": {"minY": 0.0, "minX": 1.77540714E12, "maxY": 1993.0, "series": [{"data": [[1.77540714E12, 1993.0]], "isOverall": false, "label": "Max", "isController": false}, {"data": [[1.77540714E12, 0.0]], "isOverall": false, "label": "Min", "isController": false}, {"data": [[1.77540714E12, 1400.6000000000001]], "isOverall": false, "label": "90th percentile", "isController": false}, {"data": [[1.77540714E12, 1854.94]], "isOverall": false, "label": "99th percentile", "isController": false}, {"data": [[1.77540714E12, 183.0]], "isOverall": false, "label": "Median", "isController": false}, {"data": [[1.77540714E12, 1635.8999999999999]], "isOverall": false, "label": "95th percentile", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.77540714E12, "title": "Response Time Percentiles Over Time (successful requests only)"}},
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
    data: {"result": {"minY": 1.0, "minX": 36.0, "maxY": 827.0, "series": [{"data": [[133.0, 502.0], [36.0, 827.0], [329.0, 58.0], [102.0, 71.5]], "isOverall": false, "label": "Successes", "isController": false}, {"data": [[36.0, 1.0], [329.0, 1.0]], "isOverall": false, "label": "Failures", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 1000, "maxX": 329.0, "title": "Response Time Vs Request"}},
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
    data: {"result": {"minY": 1.0, "minX": 36.0, "maxY": 827.0, "series": [{"data": [[133.0, 502.0], [36.0, 827.0], [329.0, 58.0], [102.0, 71.5]], "isOverall": false, "label": "Successes", "isController": false}, {"data": [[36.0, 1.0], [329.0, 1.0]], "isOverall": false, "label": "Failures", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 1000, "maxX": 329.0, "title": "Latencies Vs Request"}},
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
        data: {"result": {"minY": 10.0, "minX": 1.77540714E12, "maxY": 10.0, "series": [{"data": [[1.77540714E12, 10.0]], "isOverall": false, "label": "hitsPerSecond", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.77540714E12, "title": "Hits Per Second"}},
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
        data: {"result": {"minY": 0.8333333333333334, "minX": 1.77540714E12, "maxY": 6.666666666666667, "series": [{"data": [[1.77540714E12, 6.666666666666667]], "isOverall": false, "label": "200", "isController": false}, {"data": [[1.77540714E12, 2.5]], "isOverall": false, "label": "202", "isController": false}, {"data": [[1.77540714E12, 0.8333333333333334]], "isOverall": false, "label": "500", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.77540714E12, "title": "Codes Per Second"}},
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
        data: {"result": {"minY": 0.8333333333333334, "minX": 1.77540714E12, "maxY": 9.166666666666666, "series": [{"data": [[1.77540714E12, 9.166666666666666]], "isOverall": false, "label": "HTTP Request-success", "isController": false}, {"data": [[1.77540714E12, 0.8333333333333334]], "isOverall": false, "label": "HTTP Request-failure", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.77540714E12, "title": "Transactions Per Second"}},
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
        data: {"result": {"minY": 0.8333333333333334, "minX": 1.77540714E12, "maxY": 9.166666666666666, "series": [{"data": [[1.77540714E12, 9.166666666666666]], "isOverall": false, "label": "Transaction-success", "isController": false}, {"data": [[1.77540714E12, 0.8333333333333334]], "isOverall": false, "label": "Transaction-failure", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.77540714E12, "title": "Total Transactions Per Second"}},
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

