(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-4dfd0ea6"],{4959:function(e,t,r){"use strict";r.d(t,"d",(function(){return i})),r.d(t,"c",(function(){return n})),r.d(t,"a",(function(){return o})),r.d(t,"f",(function(){return s})),r.d(t,"b",(function(){return l})),r.d(t,"e",(function(){return u}));var a=r("b775");function i(e,t){return Object(a["a"])({url:"/shortUrl/group/list",method:"post",headers:{repeatSubmit:!1},params:e,data:t})}function n(){return Object(a["a"])({url:"/shortUrl/group/allList",method:"post",headers:{repeatSubmit:!1},data:null})}function o(e){return Object(a["a"])({url:"/shortUrl/group/add",method:"post",data:e})}function s(e){return Object(a["a"])({url:"/shortUrl/group/update",method:"post",data:e})}function l(e){return Object(a["a"])({url:"/shortUrl/group/del",method:"post",data:e})}function u(){return Object(a["a"])({url:"/system/dict/type/refreshCache",method:"delete"})}},fae6:function(e,t,r){"use strict";r.r(t);var a=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"app-container"},[r("el-form",{directives:[{name:"show",rawName:"v-show",value:e.showSearch,expression:"showSearch"}],ref:"queryForm",attrs:{model:e.queryParams,size:"small",inline:!0,"label-width":"68px"}},[r("el-form-item",{attrs:{label:"分组名称",prop:"groupName"}},[r("el-input",{staticStyle:{width:"240px"},attrs:{placeholder:"请输入分组名称",clearable:""},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleQuery(t)}},model:{value:e.queryBody.groupName,callback:function(t){e.$set(e.queryBody,"groupName",t)},expression:"queryBody.groupName"}})],1),r("el-form-item",[r("el-button",{attrs:{type:"primary",icon:"el-icon-search",size:"mini"},on:{click:e.handleQuery}},[e._v("搜索")]),r("el-button",{attrs:{icon:"el-icon-refresh",size:"mini"},on:{click:e.resetQuery}},[e._v("重置")])],1)],1),r("el-row",{staticClass:"mb8",attrs:{gutter:10}},[r("el-col",{attrs:{span:1.5}},[r("el-button",{directives:[{name:"hasPermi",rawName:"v-hasPermi",value:["shortUrl:group:add"],expression:"['shortUrl:group:add']"}],attrs:{type:"primary",plain:"",icon:"el-icon-plus",size:"mini"},on:{click:e.handleAdd}},[e._v("新增")])],1),r("el-col",{attrs:{span:1.5}},[r("el-button",{directives:[{name:"hasPermi",rawName:"v-hasPermi",value:["shortUrl:group:update"],expression:"['shortUrl:group:update']"}],attrs:{type:"success",plain:"",icon:"el-icon-edit",size:"mini",disabled:e.single},on:{click:e.handleUpdate}},[e._v("修改")])],1),r("el-col",{attrs:{span:1.5}},[r("el-button",{directives:[{name:"hasPermi",rawName:"v-hasPermi",value:["shortUrl:group:del"],expression:"['shortUrl:group:del']"}],attrs:{type:"danger",plain:"",icon:"el-icon-delete",size:"mini",disabled:e.multiple},on:{click:e.handleDelete}},[e._v("删除")])],1),r("el-col",{attrs:{span:1.5}},[r("el-button",{directives:[{name:"hasPermi",rawName:"v-hasPermi",value:["system:dict:remove"],expression:"['system:dict:remove']"}],attrs:{type:"danger",plain:"",icon:"el-icon-refresh",size:"mini"},on:{click:e.handleRefreshCache}},[e._v("刷新缓存")])],1),r("right-toolbar",{attrs:{showSearch:e.showSearch,columns:e.columns},on:{"update:showSearch":function(t){e.showSearch=t},"update:show-search":function(t){e.showSearch=t},queryTable:e.getList}})],1),r("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],attrs:{data:e.typeList},on:{"selection-change":e.handleSelectionChange}},[r("el-table-column",{attrs:{type:"selection",width:"55",align:"center"}}),r("el-table-column",{attrs:{label:"ID",align:"center",prop:"id",width:"50"}}),e.columns[1].visible?r("el-table-column",{attrs:{label:"分组名称",align:"center",prop:"groupName","show-overflow-tooltip":!0}}):e._e(),e.columns[2].visible?r("el-table-column",{attrs:{label:"短网址数量",align:"center",prop:"urlNum",width:"200"}}):e._e(),e.columns[3].visible?r("el-table-column",{attrs:{label:"备注",align:"center",prop:"remark","show-overflow-tooltip":!0}}):e._e(),e.columns[4].visible?r("el-table-column",{attrs:{label:"创建时间",align:"center",prop:"createTime",width:"180"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("span",[e._v(e._s(e.parseTime(t.row.createTime)))])]}}],null,!1,3078210614)}):e._e(),r("el-table-column",{attrs:{label:"操作",align:"center","class-name":"small-padding fixed-width"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-button",{directives:[{name:"hasPermi",rawName:"v-hasPermi",value:["shortUrl:group:update"],expression:"['shortUrl:group:update']"}],attrs:{size:"mini",type:"text",icon:"el-icon-edit"},on:{click:function(r){return e.handleUpdate(t.row)}}},[e._v("修改")]),r("el-button",{directives:[{name:"hasPermi",rawName:"v-hasPermi",value:["shortUrl:group:del"],expression:"['shortUrl:group:del']"}],attrs:{size:"mini",type:"text",icon:"el-icon-delete"},on:{click:function(r){return e.handleDelete(t.row)}}},[e._v("删除")])]}}])})],1),r("pagination",{directives:[{name:"show",rawName:"v-show",value:e.total>0,expression:"total>0"}],attrs:{total:e.total,page:e.queryParams.pageNum,limit:e.queryParams.pageSize},on:{"update:page":function(t){return e.$set(e.queryParams,"pageNum",t)},"update:limit":function(t){return e.$set(e.queryParams,"pageSize",t)},pagination:e.getList}}),r("el-dialog",{attrs:{title:e.title,visible:e.open,width:"500px","append-to-body":""},on:{"update:visible":function(t){e.open=t}}},[r("el-form",{ref:"form",attrs:{model:e.form,rules:e.rules,"label-width":"80px"}},[r("el-form-item",{attrs:{label:"分组名称",prop:"groupName"}},[r("el-input",{attrs:{placeholder:"请输入分组名称"},model:{value:e.form.groupName,callback:function(t){e.$set(e.form,"groupName",t)},expression:"form.groupName"}})],1),r("el-form-item",{attrs:{label:"备注",prop:"remark"}},[r("el-input",{attrs:{type:"textarea",placeholder:"请输入内容"},model:{value:e.form.remark,callback:function(t){e.$set(e.form,"remark",t)},expression:"form.remark"}})],1)],1),r("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{attrs:{type:"primary"},on:{click:e.submitForm}},[e._v("确 定")]),r("el-button",{on:{click:e.cancel}},[e._v("取 消")])],1)],1)],1)},i=[],n=(r("c740"),r("d81d"),r("4959")),o={name:"Dict",dicts:["sys_normal_disable"],data:function(){return{loading:!0,ids:[],single:!0,multiple:!0,showSearch:!0,total:0,typeList:[],title:"",open:!1,dateRange:[],queryParams:{pageNum:1,pageSize:10},queryBody:{groupName:void 0},columns:[{key:0,label:"序号",visible:!0},{key:1,label:"分组名称",visible:!0},{key:2,label:"短网址数量",visible:!0},{key:3,label:"备注",visible:!0},{key:4,label:"创建时间",visible:!0}],form:{},rules:{groupName:[{required:!0,message:"分组名称不能为空",trigger:"blur"}]}}},created:function(){this.getList()},methods:{getCurrentIndex:function(e,t){return t*(e-1)+1},getList:function(){var e=this;this.loading=!0,Object(n["d"])(this.queryParams,this.queryBody).then((function(t){e.typeList=t.rows,e.total=t.total,e.loading=!1})).catch((function(t){console.error(t),e.loading=!1}))},cancel:function(){this.open=!1,this.reset()},reset:function(){this.form={id:void 0,groupName:void 0},this.resetForm("form")},handleQuery:function(){this.queryParams.pageNum=1,this.getList()},resetQuery:function(){this.dateRange=[],this.resetForm("queryForm"),this.handleQuery()},handleAdd:function(){this.reset(),this.open=!0,this.title="添加分组"},handleSelectionChange:function(e){this.ids=e.map((function(e){return e.id})),this.single=1!=e.length,this.multiple=!e.length},handleUpdate:function(e){this.reset();var t=e.id||this.ids,r=this.typeList.findIndex((function(e){return e.id==t}));-1!==t&&(this.form=Object.assign({},this.typeList[r]),this.open=!0,this.title="修改分组")},submitForm:function(){var e=this;this.$refs["form"].validate((function(t){t&&(void 0!=e.form.id?Object(n["f"])(e.form).then((function(t){e.$modal.msgSuccess("修改成功"),e.open=!1,e.getList()})):Object(n["a"])(e.form).then((function(t){e.$modal.msgSuccess("新增成功"),e.open=!1,e.getList()})))}))},handleDelete:function(e){var t=this,r=e.id||this.ids,a=Array.isArray(r)?r:[r],i={idList:a};this.$modal.confirm("删除分组，所属的短链也将删除，是否确认删除？").then((function(){return Object(n["b"])(i)})).then((function(){t.getList(),t.$modal.msgSuccess("删除成功")})).catch((function(){}))},handleRefreshCache:function(){var e=this;Object(n["e"])().then((function(){e.$modal.msgSuccess("刷新成功"),e.$store.dispatch("dict/cleanDict")}))}}},s=o,l=r("2877"),u=Object(l["a"])(s,a,i,!1,null,null,null);t["default"]=u.exports}}]);