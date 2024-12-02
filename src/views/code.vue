<template>
    <div id="code" style="background-color:#F8F8FF">

        <el-alert title="该二维码已通过安全验证" center show-icon type="success" :closable="false">
        </el-alert>

        <div style="text-align: center;">
            <p style="font-size: x-small; margin-top: 5%; color:lightslategray">请长按下方二维码联系客服</p>
        </div>
        
        <div style="width: 100%; margin-left: auto; margin-right: auto;">
            <img src="./images/1.jpg" alt="" style="display: block; margin: auto; width: 60%; height: auto;">
        </div>


        <div style="text-align: center;">
            <p style="font-size: x-small; margin-top: 5%; color:lightslategray ;display: inline-block;">微信号：</p>
            <p style="font-size: x-small;  color:lightslategray; display: inline-block;">xcx2000521</p>
        </div>

        <div>
            <el-button style="display: block; margin: auto; width: 80%; " type="success">当前客服在线可随时联系</el-button>
        </div>


    </div>
</template>
  
<script>
import ThemePicker from "@/components/ThemePicker";

export default {
    name: "code",
    data() {
        return {

        }
    }
};
</script>
<style ></style>
  