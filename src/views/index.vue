<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="24">
        <blockquote class="text-warning" style="font-size: 14px">
          <!-- 领取阿里云通用云产品1888优惠券
          <br />
          <el-link
            href="https://www.aliyun.com/minisite/goods?userCode=brki8iof"
            type="primary"
            target="_blank"
            >https://www.aliyun.com/minisite/goods?userCode=brki8iof</el-link
          >
          <br />
          领取腾讯云通用云产品2860优惠券
          <br />
          <el-link
            href="https://cloud.tencent.com/redirect.php?redirect=1025&cps_key=198c8df2ed259157187173bc7f4f32fd&from=console"
            type="primary"
            target="_blank"
            >https://cloud.tencent.com/redirect.php?redirect=1025&cps_key=198c8df2ed259157187173bc7f4f32fd&from=console</el-link
          >
          <br /> -->
          雨云超低价服务器
          <br />
          <el-link
            href="https://www.rainyun.com/cola666_"
            type="primary"
            target="_blank"
            >https://www.rainyun.com/cola666_</el-link
          >
          <br />

          阿里云服务器折扣区
          <el-link href="https://www.aliyun.com/minisite/goods?userCode=7jj2nwfb" type="primary" target="_blank"
            >>☛☛点我进入☚☚</el-link
          >
          <!-- &nbsp;&nbsp;&nbsp; 腾讯云服务器秒杀区
          <el-link href="http://txy.ruoyi.vip" type="primary" target="_blank"
            >>☛☛点我进入☚☚</el-link
          ><br /> -->
          <!-- <h4 class="text-danger">
            云产品通用红包，可叠加官网常规优惠使用。(仅限新用户)
          </h4> -->
        </blockquote>

        <hr /> 
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>可乐短链 </h2>
        <!-- <p>
          一直想做一款后台管理系统，看了很多优秀的开源项目但是发现没有合适自己的。于是利用空闲休息时间开始自己写一套后台系统。如此有了若依管理系统，她可以用于所有的Web应用程序，如网站管理后台，网站会员中心，CMS，CRM，OA等等，当然，您也可以对她进行深度定制，以做出更强系统。所有前端后台代码封装过后十分精简易上手，出错概率低。同时支持移动客户端访问。系统会陆续更新一些实用功能。
        </p> -->
        <p>
          <b>当前版本:</b> <span>v{{ version }}</span>
        </p>
        <p>
          <el-tag type="danger">&yen;内测免费使用</el-tag>
        </p>
        <p>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-cloudy"
            plain
            @click="goTarget('https://gitee.com/xcx2000/cola')"
            >访问码云（开源代码）</el-button
          >
          <el-button
            size="mini"
            icon="el-icon-s-home"
            plain
            @click="goTarget('https://ylmcat.com')"
            >访问正式版</el-button
          >
        </p>
      </el-col>

      <!-- <el-col :sm="24" :lg="12" style="padding-left: 50px">
        <el-row>
          <el-col :span="12">
            <h2>技术选型</h2>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <h4>后端技术</h4>
            <ul>
              <li>SpringBoot</li>
              <li>Spring Security</li>
              <li>JWT</li>
              <li>MyBatis</li>
              <li>Druid</li>
              <li>Fastjson</li>
              <li>...</li>
            </ul>
          </el-col>
          <el-col :span="6">
            <h4>前端技术</h4>
            <ul>
              <li>Vue</li>
              <li>Vuex</li>
              <li>Element-ui</li>
              <li>Axios</li>
              <li>Sass</li>
              <li>Quill</li>
              <li>...</li>
            </ul>
          </el-col>
        </el-row>
      </el-col> -->
    </el-row>
    <el-divider />
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>联系信息</span>
          </div>
          <div class="body">
            <p>
              <i class="el-icon-s-promotion"></i> 官网：<el-link
                href="https://ylmcat.com"
                target="_blank"
                >https://ylmcat.com</el-link
              >
            </p>
            <p>
              <i class="el-icon-user-solid"></i> QQ群：
              <!-- <s> 满174951577 </s> -->
               <a href="http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=M6BveNmw3bpOqT8SUmC_yrTgS8e6Qv78&authKey=Nlem1oo%2FmoGd2q5DVqq0WgL6Qx2wVcpTRydgRj621sj7iLYeI3TMuG9WCMeUSd8l&noverify=0&group_code=796145844" target="_blank">796145844</a>
            </p>
            <p>
              <i class="el-icon-chat-dot-round"></i> 微信：<a
                href="javascript:;"
                >cola521x</a
              >
            </p>
            <p>
              <i class="el-icon-money"></i> QQ：<a
                href="javascript:;"
                class="支付宝信息"
                >18990027</a
              >
            </p>
          </div>
        </el-card>
      <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>更新日志</span>
          </div>
          <el-collapse accordion>
            <el-collapse-item title="v1.0.0 - 2024-01-30">
              <ol>
                <li>可乐短链 正式发布测试版本</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v1.0.1 - 2024-02-06">
              <ol>
                <li>新增短链数据统计可视化页面</li>
              </ol>
            </el-collapse-item>
          </el-collapse>
        </el-card>

      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
   <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>开源不易，打赏一下</span>
          </div>
           <div class="body">
            <img
              src="@/assets/images/sendMoney.jpg"
              alt="donate"
              width="100%"
              height="60%"
              style="display: block;"
            />
            <!-- <span style="display: inline-block; height: 30px; line-height: 30px"
              >赞赏记录:</span
            > -->
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>联系我们</span>
          </div>
          <div class="body">
            <img
              src="@/assets/images/chat.png"
              alt="donate"
              width="100%"
            />
            <span style="display: inline-block; height: 30px; line-height: 30px"
              >联系作者提供反馈意见</span
            >
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "1.0.0"
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    }
  }
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

