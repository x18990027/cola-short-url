(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2e700a44"],{"39cd":function(t,e,a){"use strict";a("7cfd")},"7cfd":function(t,e,a){},c4a8:function(t,e,a){"use strict";a.r(e);var l=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{attrs:{id:"openApi"}},[a("el-divider",[t._v("API对接文档")]),a("el-divider",[t._v("接口地址")]),a("el-divider",[t._v("https://ylmcat.com/api/createLink")]),a("el-divider",[t._v("请求方式:POST/GET ")]),a("el-divider",[t._v("请求格式:FORM/JSON ")]),a("el-divider",[t._v("返回格式:JSON")]),a("el-table",{staticStyle:{width:"100%"},attrs:{data:t.tableData}},[a("el-table-column",{staticStyle:{width:"25%"},attrs:{prop:"field",label:"字段"}}),a("el-table-column",{staticStyle:{width:"25%"},attrs:{prop:"type",label:"类型"}}),a("el-table-column",{staticStyle:{width:"25%"},attrs:{prop:"must",label:"必填"}}),a("el-table-column",{staticStyle:{width:"25%"},attrs:{prop:"remark",label:"说明"}})],1),a("el-divider",[t._v("返回参数")]),a("el-table",{staticStyle:{width:"100%"},attrs:{data:t.tableData2}},[a("el-table-column",{staticStyle:{width:"33%"},attrs:{prop:"field",label:"字段"}}),a("el-table-column",{staticStyle:{width:"33%"},attrs:{prop:"type",label:"类型"}}),a("el-table-column",{staticStyle:{width:"33%"},attrs:{prop:"remark",label:"说明"}})],1)],1)},i=[],r={name:"openApi",data:function(){return{tableData:[{field:"token",type:"String",must:"是",remark:"用户token,右上角->个人资料可查看"},{field:"domainId",type:"int",must:"是",remark:"短链接域名ID"},{field:"groupId",type:"int",must:"是",remark:"分组ID，可在分组管理里面看"},{field:"longLink",type:"String",must:"是",remark:"你要缩短的长链接"},{field:"numLimit",type:"int",must:"否",remark:"限制访问次数"},{field:"numLimitLink",type:"int",must:"否",remark:"限制访问次数达到以后，跳转到的地址"},{field:"password",type:"String",must:"否",remark:"链接访问密码"}],tableData2:[{field:"code",type:"int",remark:"处理结果：200代表成功"},{field:"msg",type:"String",remark:"返回描述"},{field:"data",type:"String",remark:"生成的短链接"}]}}},d=r,n=(a("39cd"),a("2877")),s=Object(n["a"])(d,l,i,!1,null,"4ad641ca",null);e["default"]=s.exports}}]);