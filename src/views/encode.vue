<template>
    <div id="encode">
        <el-divider><i class="el-icon-mobile-phone"></i></el-divider>
        <br> <br> <br> <br> <br> <br><br> <br> <br><br> <br> <br><br><br><br>
        <div class="container">
            <p>该页面已加密,请输入密码后访问</p>
            <el-input v-model="code" placeholder="请输入访问密码"></el-input>
            <br>
            <el-button type="success" @click="go()" style="width: 50%;" round>进入</el-button>
   
        </div>

    </div>
</template>
  
<script>
import { encodeGo } from "@/api/shortUrl/url";

export default {
    name: "encode",
    metaInfo: {
        title: '请输入访问密码...',
    },


    mounted() {
        // 设置单个页面的标题内容，覆盖全局的标题  
        document.title = '自定义标题';
    },


    data() {
        return {
            code: '',
            id: null,
        }
    },
    created() {
        // 在组件创建时获取id参数的值  
        this.id = this.$route.query.id;
    },
    watch: {
        // 监听$route对象的变化，以便在URL变化时更新id的值  
        '$route': function (to, from) {
            this.id = to.query.id;
        }
    },
    methods: {
        go: function () {
            const newForm = {
                id: this.id,
                code: this.code
                // 你可以在这里添加 form 需要的其他属性  
            };

            // fetch('localhost:2050/shortUrl/url/encodeGo', {
            //     method: 'POST', // 假设您正在发送一个POST请求  
            //     headers: {
            //         'Content-Type': 'application/json'
            //     },
            //     body: JSON.stringify(newForm),
            //     redirect: 'follow' // 告诉fetch跟随重定向  
            // })
            encodeGo(newForm).then(response => {
                const url = response.msg;
                window.location.href = url;  
            });
        }


    }



};
</script>
<style >
body,
html {
    height: 100%;
    margin: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    background-image: url("./images/encode.jpg");
    /* 背景图片铺满整个元素 */
    background-size: cover;

    /* 背景图片始终位于元素的中心 */
    background-position: center;

    /* 背景图片不重复 */
    background-repeat: no-repeat;

    /* 设置元素高度为100%，使其占据整个视口高度 */
    height: 100vh;

    /* 设置元素宽度为100%，使其占据整个视口宽度 */
    width: 100%;
}

.container {
    text-align: center;
}

.el-input {
    width: 100%;
    /* 使输入框宽度占满容器 */
    max-width: 300px;
    /* 限制最大宽度，防止在非常宽的屏幕上变得过大 */
}

/* 可选的媒体查询，用于在不同屏幕尺寸上调整样式 */
@media screen and (max-width: 600px) {
    .el-input {
        font-size: 16px;
        /* 在小屏幕上减小字体大小 */
    }

}
</style>
  