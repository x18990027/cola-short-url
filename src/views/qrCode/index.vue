<template>
  <div class="app-container">


    <el-descriptions title="" :column="2" border>
      <el-descriptions-item label="用户名" label-class-name="my-label"
        content-class-name="my-content">{{ this.userName }}</el-descriptions-item>
      <el-descriptions-item label="剩余生成次数"  >{{ this.qrOrderNum}}</el-descriptions-item>
    </el-descriptions>
    <br>
    <!-- <el-input v-model="input" style="width: 50%; margin-top: 1%;" placeholder="请输入充值卡密"></el-input> -->

    <div class="button-container">
      <el-button type="success" @click="dialogVisible = true">点我联系客服充值生成次数</el-button>
    </div>



    <el-alert title="生成次数购买价格：0.6元/次" center type="success" description="微信:xcx2000521 ;QQ :18990027" />

    <el-row :gutter="10" class="mb8" style="margin-top: 2%;">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['shortUrl:group:add']">生成</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['shortUrl:group:del']">删除</el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="typeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" width="50" v-if="false" />
      <el-table-column label="序号" align="center" width="50" v-if="columns[0].visible">
        <template slot-scope="scope">
          {{ getCurrentIndex(queryParams.pageNum, queryParams.pageSize) + scope.$index }}
        </template>
      </el-table-column>
      <el-table-column label="下载链接" align="center" prop="link" :show-overflow-tooltip="true" />
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true" v-if="columns[1].visible" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" v-if="columns[2].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['shortUrl:group:del']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />


    <el-dialog title="添加客服充值生成次数" :visible.sync="dialogVisible" width="80%">
      <el-alert style="height: 10%;" center title="微信扫码添加客服：" type="success" description="价格低至0.6元/次，多买多送！">
      </el-alert>
      <el-image :src="src" class="centered-image"></el-image>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>


    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="80%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">

        <el-form-item label="二维码类型" prop="type">
          <el-select v-model="form.type" clearable placeholder="请选择">
            <el-option v-for="item in types" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>


        <el-form-item label="二维码内容:" prop="content">
          <el-input v-model="form.content" placeholder="请输入要生成的链接或者文字" />
        </el-form-item>

        <el-form-item label="二维码背景描述" prop="prompt">
          <el-input v-model="form.prompt" placeholder="请输入二维码背景描述" />
          <el-alert title="温馨提示" type="success" description="背景描述使用英文，生成的结果会更准确！">
          </el-alert>
        </el-form-item>

        <el-form-item label="接收邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入您的邮箱地址" />
        </el-form-item>

        <el-form-item label="二维码位置" prop="position">
          <el-select v-model="form.position" clearable placeholder="请选择(默认为中间)">
            <el-option v-for="item in positions" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>


        <el-form-item label="二维码宽高比" prop="aspectRatio">
          <el-select v-model="form.aspectRatio" clearable placeholder="请选择(默认为1：1)">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
        </el-form-item>

        <el-alert title="生成提示" type="warning" description="部分过于艺术化的二维码需要用另外一台设备扫码才能识别" show-icon>
        </el-alert>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" v-loading.fullscreen.lock="fullscreenLoading">生 成</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
  
<script>

import { qrList, qrAdd, userData,delCode} from "@/api/shortUrl/url";
export default {
  name: "Dict",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      userName:'',
      qrOrderNum:undefined,
      fullscreenLoading: false,
      src: require('@/assets/images/wechat.jpg'),
      dialogVisible: false,
      num: 10,
      positions: [
        {
          value: 'top',
          label: '顶部'
        },
        {
          value: 'center',
          label: '中心'
        },
        {
          value: 'bottom',
          label: '底部'
        },
      ],
      types: [
        {
          value: 'link',
          label: '链接'
        },
        {
          value: 'text',
          label: '文字'
        }
      ],
      options: [{
        value: '1:1',
        label: '1:1'
      }, {
        value: '16:9',
        label: '16:9'
      }, {
        value: '9:16',
        label: '9:16'
      }, {
        value: '4:3',
        label: '4:3'
      }, {
        value: '3:4',
        label: '3:4'
      }],
      value: '',
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
      // 分组表格数据
      typeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      queryBody: {
        groupName: undefined
      },

      // 列信息
      columns: [
        { key: 0, label: `序号`, visible: true },
        { key: 1, label: `备注`, visible: true },
        { key: 2, label: `创建时间`, visible: true },
      ],

      // 表单参数
      form: {},
      // 表单校验
      rules: {

        content: [
          { required: true, message: "二维码内容不能为空", trigger: "blur" }
        ],
        prompt: [
          { required: true, message: "背景描述不能为空", trigger: "blur" }
        ],
        email: [
          { required: true, message: "接收邮箱不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getUser();
  },
  methods: {


    getCurrentIndex(pageNum, pageSize) {
      return pageSize * (pageNum - 1) + 1;
    },

    getUser() {
      userData().then(response => {
        this.userName = response.data.userName;
        this.qrOrderNum =response.data.qrOrderNum;
      }).catch(error => {
        console.error(error);
      });
    },

    /** 查询生成列表 */
    getList() {
      this.loading = true;
      qrList(this.queryParams).then(response => {
        this.typeList = response.rows;
        this.total = response.total;
        this.loading = false;
      }
      ).catch(error => {
        console.error(error);
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        groupName: undefined
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "生成艺术二维码";
    },

    /** 新增按钮操作 */
    invest() {
      this.reset();
      this.open = true;
      this.title = "生成次数充值";
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {

      this.reset();

      const id = row.id || this.ids
      const typeListIndex = this.typeList.findIndex(item => item.id == id);
      if (id !== -1) {
        this.form = Object.assign({}, this.typeList[typeListIndex]);
        this.open = true;
        this.title = "修改分组";
      }
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateType(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.fullscreenLoading = true;
            qrAdd(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.getUser();
              this.fullscreenLoading = false;
            }).catch(error => {
              this.fullscreenLoading = false;
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      const idList = Array.isArray(ids) ? ids : [ids];
      const param = {
        idList: idList
      };
      this.$modal.confirm('生成信息删除将无法恢复，是否确认删除？').then(function () {
        return delCode(param);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },

    handleRefreshCache() {
      refreshCache().then(() => {
        this.$modal.msgSuccess("刷新成功");
        this.$store.dispatch('dict/cleanDict');
      });
    }
  }
};
</script>
<style>
.centered-image {
  width: 30%;
  height: auto;
  margin-left: 35%;
}

/* 仅在屏幕宽度小于等于600px（例如手机设备）时应用此样式 */
@media (max-width: 600px) {
  .centered-image {
    width: 80%;
    /* 更改宽度为适合移动设备的比例 */
    margin-left: 10%;
    /* 如果需要，更改左边距 */
  }
}

/* CSS样式表中添加对应的居中样式 */
.button-container {
  display: flex;
  justify-content: center;
  /* 水平居中 */
  align-items: center;
  /* 垂直居中（如果需要） */
  width: 100%;
  /* 或者指定一个宽度以适应其所在容器 */
}
</style>