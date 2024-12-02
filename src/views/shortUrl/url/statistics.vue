<template>
    <div class="app-container">


        <el-tabs type="card" v-model="reportName" @tab-click="reportClick">
            <el-tab-pane name="report">
                <span slot="label"><i class="el-icon-s-data"></i>统计大屏</span>
            </el-tab-pane>
            <el-tab-pane label="访问历史" name="ipHistory"></el-tab-pane>
        </el-tabs>

        <!-- <div>
            <el-table v-loading="loading" :data="urlList" @selection-change="handleSelectionChange" v-show="false">
                <el-table-column type="selection" width="55" align="center" />
                <el-table-column label="ID" prop="id" width="50" v-if="false" />
                <el-table-column label="序号" align="center" width="50">
                    <template slot-scope="scope">
                        {{ getCurrentIndex(queryParams.pageNum, queryParams.pageSize) + scope.$index }}
                    </template>
                </el-table-column>
                <el-table-column label="ip" prop="shortLink" :show-overflow-tooltip="true" width="200"
                    v-if="columns[1].visible" />
                <el-table-column label="地址" prop="longLink" :show-overflow-tooltip="true" width="200"
                    show-overflow-tooltip v-if="columns[2].visible" />
                <el-table-column label="设备信息" prop="groupName" width="100" />
                <el-table-column label="访问时间" align="center" prop="createTime" width="180">
                    <template slot-scope="scope">
                        <span>{{ parseTime(scope.row.createTime) }}</span>
                    </template>
                </el-table-column>
            </el-table>
            <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize" @pagination="getList" />
        </div> -->






        <!-- <el-divider><i class="el-icon-view"></i></el-divider> -->
        <div>

            <el-row :gutter="20">
                <el-col :span="6">
                    <div>
                        <el-statistic title="今日新增访问量">
                            <template slot="formatter">
                                <span v-if="statisticsData" style="color: red;">{{ todayAddNum }}</span>
                            </template>
                        </el-statistic>
                    </div>
                </el-col>
                <el-col :span="6">
                    <div>
                        <el-statistic title="今日新增访问IP量" :value="todayAddIpNum">
                            <template slot="suffix">
                                <span style="color: red;"></span>
                            </template>
                        </el-statistic>
                    </div>
                </el-col>
                <el-col :span="6">
                    <div>
                        <el-statistic group-separator="," decimal-separator="." :value="visitsTotal" :title="title">
                            <template slot="prefix">
                                <i class="el-icon-s-flag" style="color: red"></i>
                            </template>
                            <template slot="suffix">
                                <i class="el-icon-s-flag" style="color: blue"></i>
                            </template>
                        </el-statistic>
                    </div>
                </el-col>
                <el-col :span="6">
                    <div>
                        <el-statistic :value="ipTotal" title="访问IP总量">
                            <template slot="suffix">
                                <span @click="like = !like" class="like">
                                    <i class="el-icon-star-on" style="color:red" v-show="!!like"></i>
                                    <i class="el-icon-star-off" v-show="!like"></i>
                                </span>
                            </template>
                        </el-statistic>
                    </div>
                </el-col>
            </el-row>
        </div>
        <el-divider><i class="el-icon-mobile-phone"></i></el-divider>
        <div>


            <div style="display: flex; align-items: center;" v-show="showReport">
                <p style="margin-left: 7%; font-weight: bold;">流量趋势(点击量)</p>
                <el-tabs v-model="activeName" style="margin-left: 1%;" type="card" @tab-click="handleClick">
                    <el-tab-pane label="今日" name="first"></el-tab-pane>
                    <el-tab-pane label="昨日" name="second"></el-tab-pane>
                    <el-tab-pane label="最近7日" name="third"></el-tab-pane>
                </el-tabs>
            </div>




            <div id="7DaysStatistics" style="width: 1500px;height:400px;" v-show="showReport">
            </div>
        </div>
        <el-divider v-if="showReport"></el-divider>

        <div style="display: inline-block;" v-show="showReport">
            <p style="margin-left: 9%; font-weight: bold; ">地区访问量TOP10</p>
            <div id="areaStatistics" style="width: 100%; width: 850px; height: 400px; display: inline-block;"></div>
        </div>
        <div style="display: inline-block;" v-show="showReport">
            <p style="font-weight: bold;  margin-left: 45%">访问终端分布</p>
            <div id="main" style="width: 550px; height: 400px; margin-left: 8%;  display: inline-block;"></div>
        </div>

    </div>
</template>

<script>
import * as echarts from 'echarts';
import { getStatistics } from "@/api/shortUrl/url";

export default {
    name: "Dict",
    dicts: ['sys_normal_disable'],
    data() {
        return {
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
            },


            reportName: "report",
            showReport: true,
            //顶部访问统计
            like: true,
            visitsTotal: 0,
            title: "访问总量",
            todayAddNum: 0,
            ipTotal: 0,
            todayAddIpNum: 0,

            //7天日期折线图参数
            chartDom: null,
            myDaysChart: null,
            daysOption: null,
            hoursList: ["01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"],
            daysList: null,
            xData: [],
            yData: [],

            //地区统计折线图
            areaChartInstance: null,
            areaOption: null,
            activeName: 'first',
            areaData: [],

            //终端访问统计
            chartInstance: null,
            option: null,
            statisticsData: null,
            terminalData: [],
            queryBody: {
                id: undefined,
            },



        };
    },


    created() {
        const urlId = this.$route.params && this.$route.params.urlId;
        if (urlId !== undefined && urlId != 0) {
            this.queryBody.id = urlId;
            this.getData();
        } else {
            this.getData();
        }

    },
    mounted() {
        this.initChartsAfterDataLoaded();
    },
    methods: {
        /** 查询统计数据 */
        getData() {
            this.loading = true;

            getStatistics(this.queryBody).then(response => {

                this.statisticsData = response.rows[0];
                this.todayAddNum = this.statisticsData.todayAddNum;
                this.visitsTotal = this.statisticsData.visitsTotal;
                this.ipTotal = this.statisticsData.ipTotal;
                this.todayAddIpNum = this.statisticsData.todayAddIpNum;


                this.xData = this.hoursList;
                this.yData = this.statisticsData.todayNumList;

                this.areaData = this.statisticsData.cityList;

                this.terminalData = this.statisticsData.terminalList;
                this.loading = false;
                this.initChartsAfterDataLoaded();
            }
            ).catch(error => {
                console.error(error);
                this.loading = false;
            });
        },

        //初始化图表
        initChartsAfterDataLoaded() {
            this.initDaysChart();
            this.initAreaChart();
            this.initChart();
        },

        reportClick(tab, event) {
            if (tab.name === "report") {
                this.reportName = "report";
                this.showReport = true;
            } else if (tab.name === "ipHistory") {
                this.reportName = "report";
                this.showReport = false;
            }
        }
        ,

        handleClick(tab, event) {
            // 你可以在这里根据点击的标签页来执行不同的逻辑  
            if (tab.name === 'first') {
                this.activeName = 'first';
                this.xData = this.hoursList;
                this.yData = this.statisticsData.todayNumList;
            } else if (tab.name === 'second') {
                this.activeName = 'second'
                this.xData = this.hoursList;
                this.yData = this.statisticsData.yesterdayNumList;
            } else if (tab.name === 'third') {
                this.activeName = 'third'
                this.xData = this.getRecent7Days();
                this.yData = this.statisticsData.daysStatistics;
            }
            // 更新图表数据  
            this.initDaysChart();
        },


        initDaysChart() {
            this.chartDom = document.getElementById('7DaysStatistics');
            this.myDaysChart = echarts.init(this.chartDom);
            this.daysOption = {
                xAxis: {
                    type: 'category',
                    data: this.xData
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        data: this.yData,
                        type: 'line',
                        smooth: true
                    }
                ]
            };
            this.myDaysChart.setOption(this.daysOption);
        },

        //获取最近7天的时间
        getRecent7Days() {
            const today = new Date(); // 获取当前日期  
            const days = []; // 存储最近7天的日期字符串  
            // 生成最近7天的日期字符串数组（包括今天）  
            for (let i = 6; i >= 0; i--) {
                const date = new Date(today.getTime() - (i * 24 * 60 * 60 * 1000));
                days.unshift(date.toISOString().slice(0, 10)); // 将日期转换为字符串格式并添加到数组的开头  
            }

            // 对日期字符串数组进行排序（从小到大）  
            days.sort();
            return days; // 返回按时间从小到大排序的日期字符串数组  
        },


        initAreaChart() {
            const chartDom = document.getElementById('areaStatistics');
            this.areaChartInstance = echarts.init(chartDom);

            this.areaOption = {
                dataset: {
                    source: this.areaData
                },
                grid: { containLabel: true },
                xAxis: { name: '访问量' },
                yAxis: { type: 'category' },

                series: [
                    {
                        type: 'bar',
                        encode: {
                            x: 'num',
                            y: 'address'
                        }
                    }
                ]
            };

            this.areaChartInstance.setOption(this.areaOption);
        },


        initChart() {
            const chartDom = document.getElementById('main');

            // 初始化ECharts实例
            this.chartInstance = echarts.init(chartDom);

            // 定义图表配置项
            this.option = {
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    top: '5%',
                    left: 'center'
                },
                series: [
                    {
                        name: 'Access From',
                        type: 'pie',
                        radius: ['40%', '70%'],
                        avoidLabelOverlap: false,
                        label: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: 40,
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: this.terminalData
                    }
                ]
            };

            // 设置图表的配置项
            this.chartInstance.setOption(this.option);
        },

    },




    beforeDestroy() {
        if (this.areaChartInstance) {
            this.chartInstance.dispose();
        }
        if (this.chartInstance) {
            this.chartInstance.dispose(); // 组件销毁时释放图表资源
        }
    },




};
</script>

<style lang="scss">
.like {
    cursor: pointer;
    font-size: 25px;
    display: inline-block;



}
</style>