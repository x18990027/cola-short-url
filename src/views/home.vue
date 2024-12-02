<template>
  <div id="home">

<!-- Header -->
<header class="header-main header-light fixed-top fixed-header">
        <nav class="navbar navbar-expand-lg navbar-light">
            <div class="container">
                <!-- Logo -->
                <a class="navbar-brand" href="https://www.ylb6.cn/">
                    <img src="./files/logo.png">
                </a>
                <!-- Logo -->
                <!-- Mobile Toggle -->
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <!-- End Mobile Toggle -->
                <!-- Menu -->
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item active"><a class="nav-link" href="https://www.ylb6.cn/"
                                target="_blank">首页</a></li>
                        <li class="nav-item ">
                         
                          <router-link class="nav-link" :to="'/login'">用户登录</router-link>
                      
                        </li>
                          
                         <li class="nav-item ">
                          <!-- <a class="nav-link" href="https://kw.sorul.cn/">购买会员</a>  -->
                          <router-link class="nav-link" :to="'/register'">用户注册</router-link>
                        </li>
                    </ul>
                </div>
                <!-- End Menu -->
            </div>
        </nav>
    </header>
    <!-- End Header -->
    <!-- Main -->
    <main>
        <!-- Home Banner -->
        <section class="effect-section pt-9">
            <div class="svg-style-top-right opacity-1">
                <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" x="0px"
                    y="0px" viewBox="0 0 361.1 384.8" style="enable-background:new 0 0 361.1 384.8;"
                    xml:space="preserve" class="injected-svg svg_img text-primary">
                    <path
                        d="M53.1,266.7C19.3,178-41,79.1,41.6,50.1S287.7-59.6,293.8,77.5c6.1,137.1,137.8,238,15.6,288.9 S86.8,355.4,53.1,266.7z">
                    </path>
                </svg>
            </div>
            <div class="svg-style-bottom-left opacity-1">
                <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" x="0px"
                    y="0px" viewBox="0 0 361.1 384.8" style="enable-background:new 0 0 361.1 384.8;"
                    xml:space="preserve" class="injected-svg svg_img text-primary">
                    <path
                        d="M53.1,266.7C19.3,178-41,79.1,41.6,50.1S287.7-59.6,293.8,77.5c6.1,137.1,137.8,238,15.6,288.9 S86.8,355.4,53.1,266.7z">
                    </path>
                </svg>
            </div>


            <div class="container">
                <div class="row align-items-center justify-content-center pt-9 mt-9">
                    
                    <div class="col-lg-8 col-xl-7 text-center">
                      <br>  <br>  <br>  <br>  <br>
                        <!-- <h2 class="display-5">简单易用 <mark>媒体引流</mark> 工具</h2>
                        <p class="lead">集短链系统、活码系统、个人号二维码营销解决于一体</p> -->
                    </div>
                </div>
            </div>
        </section>


        <!-- End Home Banner -->

        <!-- End Home Banner -->
        <!-- Section -->

        <!-- Section -->
        <section class="section bg-gray-100">
            <div class="container">

                <div class="col-lg-8 col-xl-7 text-center" style="margin-left: auto; margin-right: auto; width: 50%;">
                    <h2 class="display-5">简单易用 <mark>媒体引流</mark> 工具</h2>
                    <p class="lead">集短链系统、活码系统、个人号二维码营销解决于一体</p>
                </div>

                <div class="row">
                    <div class="col-lg-3 col-sm-6 my-3">
                        <div class="card text-center hover-top">
                            <div class="card-body px-3">
                                <div class="icon icon-info icon-lg text-white mb-4 rounded-circle">
                                    <i class="fas fa-link"></i>
                                </div>
                                <h5 class="mb-4">网址缩短</h5>
                                <p>支持HTTPS<br>多个可选域名<br>随时可修改</p>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-3 col-sm-6 my-3">
                        <div class="card text-center hover-top">
                            <div class="card-body px-3">
                                <div class="icon icon-warning icon-lg text-white mb-4 rounded-circle">
                                    <i class="fas fa-code"></i>
                                </div>
                                <h5 class="mb-4">活码系统</h5>
                                <p>微信群/个人号<br>二维码随时更新<br>智能切换</p>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-3 col-sm-6 my-3">
                        <div class="card text-center hover-top">
                            <div class="card-body px-3">
                                <div class="icon icon-success icon-lg text-white mb-4 rounded-circle">
                                    <i class="fas fa-sitemap"></i>
                                </div>
                                <h5 class="mb-4">多维数据分析</h5>
                                <p>支持多渠道数据追踪<br>识别访问各种设备<br>强大的统计报表</p>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-3 col-sm-6 my-3">
                        <div class="card text-center hover-top">
                            <div class="card-body px-3">
                                <div class="icon icon-danger icon-lg text-white mb-4 rounded-circle">
                                    <i class="fas fa-shield-alt"></i>
                                </div>
                                <h5 class="mb-4">高效可用</h5>
                                <p>操作设置简单通畅<br>防劫持，无恶意广告<br>稳定性（SLA）99.9%</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- End Section -->
        <!-- Section -->
        <section class="section">

            <div class="container">
                <div class="row">
                    <div class="col-lg-6 my-4">
                        <!-- <img class="img-fluid" src="./files/feature-banner-2.jpg"> -->
                    </div>
                    <div class="col-lg-6 my-4">
                        <div class="ps-lg-7">

                            <p class="lead">
                                <!-- 当您使用引流基地时，它不仅仅是一个链接，而且是你和它们之间的媒介。 -->
                            </p>
                            <div class="row pt-3">
                                <div class="col-6">
                                    <div class="icon icon-lg icon-primary-light mb-4 rounded-circle">
                                        <i class="fas fa-link"></i>
                                    </div>
                                    <!-- <h5>链接缩短</h5>
                                    <p>将原始网页链接快速生成短网址。</p> -->
                                </div>
                                <div class="col-6">
                                    <div class="icon icon-lg icon-primary-light mb-4 rounded-circle">
                                        <svg xmlns="http://www.w3.org/2000/svg"
                                            xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px"
                                            viewBox="0 0 24 24" version="1.1" class="injected-svg svg_icon">
                                            <title>General / Settings-1</title>
                                            <g id="General-/-Settings-1" stroke="none" stroke-width="1" fill="none"
                                                fill-rule="evenodd">
                                                <rect id="bound" x="0" y="0" width="24" height="24"></rect>
                                                <path
                                                    d="M7,3 L17,3 C19.209139,3 21,4.790861 21,7 C21,9.209139 19.209139,11 17,11 L7,11 C4.790861,11 3,9.209139 3,7 C3,4.790861 4.790861,3 7,3 Z M7,9 C8.1045695,9 9,8.1045695 9,7 C9,5.8954305 8.1045695,5 7,5 C5.8954305,5 5,5.8954305 5,7 C5,8.1045695 5.8954305,9 7,9 Z"
                                                    id="Combined-Shape" fill="#000000"></path>
                                                <path
                                                    d="M7,13 L17,13 C19.209139,13 21,14.790861 21,17 C21,19.209139 19.209139,21 17,21 L7,21 C4.790861,21 3,19.209139 3,17 C3,14.790861 4.790861,13 7,13 Z M17,19 C18.1045695,19 19,18.1045695 19,17 C19,15.8954305 18.1045695,15 17,15 C15.8954305,15 15,15.8954305 15,17 C15,18.1045695 15.8954305,19 17,19 Z"
                                                    id="Combined-Shape" fill="#000000" opacity="0.3"></path>
                                            </g>
                                        </svg>
                                    </div>
                                    <!-- <h5>链接功能多样化</h5>
                                    <p>网址分组，更好的管理您的链接。</p> -->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section> 
        <!-- End Section -->
        <!-- Section -->
        <!-- <section class="section">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-lg-6 py-3">
                        <img class="img-fluid" src="./files/167943708147.jpg" title="" alt="">
                    </div>
                    <div class="col-lg-6 ps-lg-9 py-3">
                        <h3 class="h1 mb-3">强大的数据统计分析报表</h3>
                        <p class="lead">无缝对接，无需单独统计。辅以完备的指导说明，践行简单易用的理念。</p>
                        <div class="row pt-1">
                            <div class="col-sm-6">
                                <ul class="list-type-01">
                                    <li>运营商统计</li>
                                    <li>访问地区统计</li>
                                    <li>IP/UV/PV统计</li>
                                    <li>访问设备统计</li>
                                </ul>
                            </div>
                            <div class="col-sm-6">
                                <ul class="list-type-01">
                                    <li>地图热点图</li>
                                    <li>访问趋势图</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
      

     
        <section class="section bg-gray-100">
      <div class="container">
        <div class="row justify-content-center section-heading">
          <div class="col-lg-8 col-xl-7 text-center">
            <h2 class="display-8">短网址服务方案</h2>
          </div>
        </div>
        <div class="row text-center">
          <div class="table-responsive">
            <table class="table text-nowrap">
              <tbody>
                <tr class="first-tr-top">
                  <th class="left-stlye xiang-mu">用户等级</th>
                  <th>免费</th>
                  <th class="shorturl-color1">会员</th>
                </tr>
                <tr>
                  <td class="left-stlye">价格</td>
                  <td class="text-danger">￥0/月</td>
                  <td class="text-danger">￥26/月</td>
                </tr>
                <tr>
                  <td class="left-stlye">生成短网址</td>
                  <td><i class="fas fa-times"></i></td>
                  <td><i class="fas fa-check text-warning"></i></td>
                </tr>
                                 <tr>
                  <td class="left-stlye">修改跳转网址</td>
                  <td><i class="fas fa-times"></i> </td>
                  <td><i class="fas fa-check text-warning"></i></td>
                </tr>
                <tr>
                  <td class="left-stlye">短网址有效性</td>
                  <td>永久有效</td>
                  <td>永久有效</td>
                </tr>
                <tr>
                  <td class="left-stlye">短网址跳转</td>
                  <td>无限制</td>
                  <td>无限制</td>
                </tr>
                <tr>
                  <td class="left-stlye">短网址创建数量</td>
                  <td><i class="fas fa-times"></i> </td>
                  <td>每天500条</td>
                </tr>
                <tr>
                  <td class="left-stlye">访问次数统计</td>
                  <td><i class="fas fa-times"></i></td>
                  <td><i class="fas fa-check text-warning"></i></td>
                </tr>
                
                <tr>
                  <td class="left-stlye">设置短网址过期时间</td>
                  <td><i class="fas fa-times"></i></td>
                  <td><i class="fas fa-check text-warning"></i></td>
                </tr>
                <tr>
                  <td class="left-stlye">查看统计报表</td>
                  <td><i class="fas fa-times"></i> </td>
                  <td><i class="fas fa-check text-warning"></i></td>
                </tr>
                <tr>
                  <td class="left-stlye">限制短网址访问次数</td>
                  <td><i class="fas fa-times"></i></td>
                  <td><i class="fas fa-check text-warning"></i></td>
                </tr>


              </tbody>
            </table>
          </div>
        </div>
      </div>
    </section> -->
        <!-- End Section -->
        <!-- Section -->
        <!-- End Section -->
        <!-- Section -->

    </main>

    <footer style="color: aliceblue; background-color: rgb(35, 79, 115);">
        <div class="footer-top">
        <div class="container">
        <div class="row justify-content-center text-center" style="color: bisque;">
            <p>违规举报/删除/联系我们 :</p>
            <a style="color: rgb(216, 220, 224);" href="mailto:18990027@qq.com">邮箱：18990027@qq.com</a>

        </div>
        </div>
        <!-- </div> -->
        <!-- <div class="footer-bottom footer-border"> -->
        <div class="container">
            <p class="m-0 text-white-50 text-center py-3" style="font-size: xx-small">
                重要声明：本站旨为给用户提供引流工具支持，具体用途与本站无关，请自觉遵守互联网的相关法律法规！<br>
                <!-- <a href="https://beian.miit.gov.cn/" rel="nofollow" target="_blank"
                    se_prerender_url="loading">
                    鄂ICP备2024035958号>
                </a>-->
                ©2024-2025 可乐短链 
            </p>
        </div>
        </div>



    </footer>
    <!-- End Footer -->


  </div>
</template>
  
<script >
import i from "./files/bootstrap.bundle.min.js.下载"
export default {

  name: "home",


  data() {
    return {

    }
  }

};
</script>
<style scoped>
@import './files/style.css';
</style>
  