<template>
    <div id="openUrl">
     
    </div>
</template>
  
<script>
import { jumpUrl} from "@/api/shortUrl/openUrl";

export default {

    name: "home",
  

    data() {
        return {
  
        }
        
    },
    created() {
    this.getUrl();
  },

  methods: {
      getUrl() {
   
      jumpUrl().then(response => {
      
      }
      ).catch(error => {
        console.error(error);
      
      });
    }
  }
    

};
</script>
  <!-- <style scoped>
  #app .theme-picker {
    display: none;
  }
  </style> -->
  