<template>
    <div class="centered-container">

        <iframe :src="iframeUrl" frameborder="0" class="centered-iframe"></iframe>
    </div>
</template>
  
<script>
import { open } from "@/api/shortUrl/url";

export default {
    name: "encode",
    metaInfo: {
        title: '正在加载中...',
    },


    mounted() {
        // 设置单个页面的标题内容，覆盖全局的标题  
        document.title = '正在加载中...';
    },


    data() {
        return {
            code: '',
            link: null,
            iframeUrl: undefined
        }
    },
    created() {
    
        // 在组件创建时获取id参数的值  
        this.link = this.$route.query.link;
        this.geUrl();
 
    },
    watch: {
        // 监听$route对象的变化，以便在URL变化时更新link的值  
        '$route': function (to, from) {
            this.link = to.query.link;
        }
    },
    methods: {
        /** 查询长链 */
        geUrl() {
            const body = {
                code: this.link,
            };
             open(body).then(response => {
               this.iframeUrl = response.msg;
                 
            });
        },

    }



};
</script>
<style ></style>
<style scoped>  .centered-container {
      display: flex;
      justify-content: center;
      /* 水平居中 */
      align-items: center;
      /* 垂直居中 */
      height: 100vh;
      /* 容器高度占满视口高度 */
      max-width: 100%;
      /* 最大宽度不超过视口宽度 */
      max-height: 100%;
      /* 最大高度不超过视口高度 */
  }

  .centered-iframe {
      margin-left: 0%;
      margin-top: 0%;
      width: 100%;
      /* iframe宽度占容器的50% */
      max-width: 100%;
      /* 最大宽度不超过容器宽度 */
      height: 100%;
      /* 高度自动，以保持内容的比例 */
      border: none;
      /* 隐藏边框 */
  }
</style>