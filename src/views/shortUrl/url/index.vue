<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="选择分组" v-model="queryBody.groupId">
        <el-select v-model="groupName" placeholder="请选择" @change="handleGroupChange">
          <el-option v-for="item in groupList" :key="item.id" :label="item.groupName" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="短网址链接" prop="shortLink">
        <el-input v-model="queryBody.shortLink" placeholder="请输入短网址链接" clearable style="width: 240px"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryBody.status" placeholder="状态" clearable style="width: 240px">
          <el-option v-for="dict in dict.type.url_status_type" :key="dict.value" :label="dict.label"
            :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker v-model="queryBody.dateRange" style="width: 240px" value-format="yyyy-MM-dd" type="daterange"
          range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['shortUrl:url:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['shortUrl:url:update']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['shortUrl:url:del']">删除</el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>

    </el-row>


    <el-table v-loading="loading" :data="urlList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" prop="id" width="50" v-if="false" />
      <el-table-column label="序号" align="center" width="50" v-if="columns[0].visible">
        <template slot-scope="scope">
          {{ getCurrentIndex(queryParams.pageNum, queryParams.pageSize) + scope.$index }}
        </template>
      </el-table-column>
      <el-table-column label="短网址" prop="shortLink" :show-overflow-tooltip="true" width="200"
        v-if="columns[1].visible" />
      <el-table-column label="跳转网址" prop="longLink" :show-overflow-tooltip="true" width="200" 
        v-if="columns[2].visible" />
      <el-table-column label="分组" prop="groupName" width="100" v-if="columns[3].visible" />
      <el-table-column label="备注" prop="remark" width="150" v-if="columns[4].visible" />
      <el-table-column label="到期时间" prop="expiryTime" width="180" v-if="columns[5].visible" />
      <el-table-column label="访问量" prop="visitsNum" width="100" v-if="columns[6].visible" />

      <el-table-column label="状态" align="center" width="100" v-if="columns[7].visible">

        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" @change="handleStatusChange(scope.row)"></el-switch>
        </template>
      </el-table-column>

      <el-table-column label="创建时间" align="center" prop="createTime" width="180" v-if="columns[8].visible">

        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>

      <el-table-column label="访问密码" prop="accessPassword" width="80" v-if="columns[9].visible" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">

        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['shortUrl:url:update']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['shortUrl:url:del']">删除</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="goToStatistics(scope.row)"
            v-hasPermi="['shortUrl:url:update']">统计</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改链接配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <!-- <el-form-item label="短链域名" >
        <el-select v-model="urlName" placeholder="请选择" @change="handleUrlKeyChangeSelect">
          <el-option v-for="item in urlkeyList" :key="item.urlType" :label="item.urlName" :value="item.urlType">
          </el-option>
        </el-select>
      </el-form-item>  -->

        <el-form-item label="短链域名" prop="domainId" v-show="domainUrl">
          <el-select v-model="form.domainId" placeholder="请选择" @change="handleDomainChangeSelect">
            <el-option v-for="item in domainList" :key="item.domainId" :label="item.domainName"
              :value="item.domainId">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="短链接分组" prop="groupId">
          <el-select style="display: inline-block;" v-model="form.groupId" placeholder="请选择"
            @change="handleGroupChangeSelect">
            <el-option v-for="item in groupList" :key="item.id" :label="item.groupName" :value="item.id">
            </el-option>
          </el-select>

          <router-link style="display: inline-block;" class="nav-link" :to="'/shortUrl/group'">
            <el-button style="background-color: rgb(191, 233, 233);">
              创建分组
            </el-button>
          </router-link>

        </el-form-item>
        <el-form-item label="默认跳转" prop="longLink">
          <el-input v-model="form.longLink" placeholder="请输入默认跳转链接" />
        </el-form-item>

        <el-form-item label="到期时间" prop="expiryTime">
          <el-date-picker label="到期时间" v-model="form.expiryTime" type="date" placeholder="选择日期">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="短链备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入短链备注"></el-input>
        </el-form-item>

        <el-collapse>
          <el-collapse-item title="高级设置" style="font-size: 14px;color: #606266;font-weight: 700" name="1">

            <el-form-item label="最大访问次数" prop="numLimit">
              <el-input-number v-model="form.numLimit" controls-position="right" :min="null" />
            </el-form-item>

            <el-form-item label="达到次数网址" prop="numLimitLink">
              <el-input v-model="form.numLimitLink" placeholder="超过最大访问次数后访问的网站" />
            </el-form-item>

            <el-form-item label="最大访问ip数" prop="ipLimit">
              <el-input-number v-model="form.ipLimit" controls-position="right" :min="null" />
            </el-form-item>

            <!-- <el-form-item label="访问密码" prop="accessPassword">
              <el-input v-model="form.accessPassword" placeholder="请输入4位(数字字母组合)密码" />
            </el-form-item>

            <el-form-item label=" 红白拦截强开" prop="redStatus">
              <el-switch style="margin-left: 2%;" v-model="form.redStatus" active-color="#13ce66"
                inactive-color="#ff4949">
              </el-switch>
              <el-alert title="强开用于被QQ和微信拦截的链接,未被拦截请勿打开,以免影响正常使用" type="warning" :closable="false">
              </el-alert>
            </el-form-item> -->

          </el-collapse-item>
        </el-collapse>

      </el-form>


      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" v-loading.fullscreen.lock="fullscreenLoading">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { urlList, addUrl, changeStatus, updateUrl, delUrl } from "@/api/shortUrl/url";
import { listSelect as groupList } from "@/api/shortUrl/group";
import {getDomainAll } from "@/api/shortUrl/domain";



export default {
  name: "Role",
  dicts: ['url_status_type'],
  data() {
    return {
      fullscreenLoading: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,

      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 角色表格数据
      urlList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层（数据权限）
      openDataScope: false,
      menuExpand: false,
      menuNodeAll: false,
      deptExpand: true,
      deptNodeAll: false,
      domainUrl: true,
      // 日期范围
      dateRange: [],
      // 数据范围选项


      // 列信息
      columns: [
        { key: 0, label: `序号`, visible: true },
        { key: 1, label: `短网址`, visible: true },
        { key: 2, label: `跳转网址`, visible: true },
        { key: 3, label: `分组`, visible: true },
        { key: 4, label: `备注`, visible: true },
        { key: 5, label: `到期时间`, visible: true },
        { key: 6, label: `访问量`, visible: true },
        { key: 7, label: `状态`, visible: true },
        { key: 8, label: `创建时间`, visible: true },
        { key: 9, label: `访问密码`, visible: true },
      ],



      domainList: [
        {
          domainId: 1,
          domainName: 'm.ylb6.cn测试专用'
        }
        ,
        {
          domainId: 2,
          domainName: 'ylm2.cn【普通跳转】'
        },
        {
          domainId: 3,
          domainName: '自定义【联系管理员】'
        }
      ],
      ddomainId: 1,
      domainName: "",
      groupList: [],
      groupListSelect: [],
      groupName: "",

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      queryBody: {
        shortLink: undefined,
        status: undefined,
        groupId: undefined
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },

      // 表单校验
      rules: {
        groupId: [
          { required: true, message: "短链分组不能为空", trigger: "blur" }
        ],
        roleKey: [
          { required: true, message: "权限字符不能为空", trigger: "blur" }
        ],
        roleSort: [
          { required: true, message: "角色顺序不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getgroupList();
    this.getDomainAll();
  },
  methods: {


    handleDomainChangeSelect(value) {
      this.form.domainId = value; // 将选中的urlKey值赋给urlKey 
    },

    //分组下拉框事件
    handleGroupChange(value) {
      this.queryBody.groupId = value; // 将选中的id值赋给groupId  
    },

    handleGroupChangeSelect(value) {
      this.form.groupId = value; // 将选中的id值赋给groupId  
    },


    //计算序号值 
    getCurrentIndex(pageNum, pageSize) {
      return pageSize * (pageNum - 1) + 1;
    },

    /** 查询短链列表 */
    getList() {
      this.loading = true;
      urlList(this.queryParams, this.queryBody).then(response => {
        this.urlList = response.rows;
        this.total = response.total;
        this.loading = false;
      }
      ).catch(error => {
        console.error(error);
        this.loading = false;
      });
    },
    /** 查询分组列表 */
    getgroupList() {
      groupList().then(response => {
        const groupList = [response.rows]
        this.groupList = response.rows;
      });
    },


   /** 查询域名列表 */
    getDomainAll() {
      getDomainAll().then(response => {
        this.domainList = response.rows;
      });
    },


    // 链接状态修改
    handleStatusChange(row) {
      let text = row.status == "1" ? "开启" : "停用";
      this.$modal.confirm('确认要"' + text + '""' + row.shortLink + '"链接吗？').then(function () {

        const body = {
          id: row.id,
          status: row.status
        };
        return changeStatus(body);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function () {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消按钮（数据权限）
    cancelDataScope() {
      this.openDataScope = false;
      this.reset();
    },
    // 表单重置
    reset() {
      if (this.$refs.menu != undefined) {
        this.$refs.menu.setCheckedKeys([]);
      }
      this.menuExpand = false,
        this.form = {
          id: undefined,
          shortLink: undefined,
          longLink: undefined,
          groupName: undefined,
          remark: undefined,
          expiryTime: undefined,
          visitsNum: undefined,
          status: undefined,
          domainId: undefined,
          remark: undefined
        };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.groupName = "";
      this.queryBody.status = "";
      this.queryBody.shortLink = "";
      this.queryBody.dateRange = undefined;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "handleDataScope":
          this.handleDataScope(row);
          break;
        case "handleAuthUser":
          this.handleAuthUser(row);
          break;
        default:
          break;
      }
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.domainUrl = true;
      this.open = true;
      this.title = "新增短链";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      const urlListIndex = this.urlList.findIndex(item => item.id == id);
      if (id !== -1) {
        this.form = Object.assign({}, this.urlList[urlListIndex]);
        this.domainUrl = false;
        this.open = true;
        this.title = "修改短链";
      }
    },

    /** 数据统计操作 */
    goToStatistics(row) {
      const urlId = row.id || 0;
      this.$router.push('/shortUrl/urlStatistics/index/' + urlId)
    },


    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            this.fullscreenLoading = true;
            updateUrl(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.fullscreenLoading = false;
            }).catch(error => {
              this.open = false;
              this.fullscreenLoading = false;
            });
          } else {
            this.fullscreenLoading = true;
            addUrl(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.fullscreenLoading = false;
            }).catch(error => {
              this.open = false;
              this.fullscreenLoading = false;
            });
          }
        }
      });
    },
    /** 提交按钮（数据权限） */
    submitDataScope: function () {
      if (this.form.id != undefined) {
        this.form.deptIds = this.getDeptAllCheckedKeys();
        dataScope(this.form).then(response => {
          this.$modal.msgSuccess("修改成功");
          this.openDataScope = false;
          this.getList();
        });
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      const idList = Array.isArray(ids) ? ids : [ids];
      const param = {
        idList: idList
      };
      this.$modal.confirm('删除后数据将无法恢复,是否确认？').then(function () {
        return delUrl(param);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },

  }
};
</script>