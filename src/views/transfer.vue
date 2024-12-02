<template>
  <!-- <div id="transfe" targe="" style="width: 100%;  height: 100%; overflow: auto; background-color: black;"> -->



  <div class="centered-container">  
    
    <iframe :src="iframeUrl" frameborder="0" class="centered-iframe"></iframe>  
  </div>  

</template>

<script>




export default {


  name: "transfer",
  metaInfo: {
    title: '正在加载中...',
  },
  mounted() {
    // 设置单个页面的标题内容，覆盖全局的标题  
    document.title = '自定义标题';
  },

  data() {
    return {
      iframeUrl: 'http://cm.351qk.cn/' // 初始的iframe URL
    }
  },




}

</script>

<style scoped>  
.centered-container {  
  display: flex;  
  justify-content: center; /* 水平居中 */  
  align-items: center; /* 垂直居中 */  
  height: 100vh; /* 容器高度占满视口高度 */  
  max-width: 100%; /* 最大宽度不超过视口宽度 */  
  max-height: 100%; /* 最大高度不超过视口高度 */  
}  
  
.centered-iframe {  
  margin-left:2%;
  margin-top: 2%;
  width: 100%; /* iframe宽度占容器的50% */  
  max-width: 100%; /* 最大宽度不超过容器宽度 */  
  height: 100%; /* 高度自动，以保持内容的比例 */  
  border: none; /* 隐藏边框 */  
}  
</style>