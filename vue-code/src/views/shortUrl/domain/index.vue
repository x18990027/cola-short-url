<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['shortUrl:domain:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['shortUrl:domain:update']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['shortUrl:domain:del']"
          >删除</el-button
        >
      </el-col>

      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
        :columns="columns"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="typeList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        label="ID"
        align="center"
        prop="id"
        width="50"
        v-if="false"
      />
      <el-table-column
        label="序号"
        align="center"
        width="50"
        v-if="columns[0].visible"
      >
        <template slot-scope="scope">
          {{
            getCurrentIndex(queryParams.pageNum, queryParams.pageSize) +
            scope.$index
          }}
        </template>
      </el-table-column>
      <el-table-column
        label="域名"
        align="center"
        prop="domain"
        width="250"
        v-if="columns[1].visible"
      />
      <el-table-column
        label="名称"
        align="center"
        prop="name"
        width="300"
        v-if="columns[2].visible"
      />
      <el-table-column
        label="是否SSL"
        align="center"
        prop="ssl"
        width="180"
        v-if="columns[3].visible"
      />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        width="200"
        v-if="columns[4].visible"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['shortUrl:group:update']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['shortUrl:group:del']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="域名" prop="domain">
          <el-input v-model="form.domain" placeholder="请输入域名（例如：ylmcat.com）" />
        </el-form-item>


         <el-form-item label="是否SSL" prop="sslStatus">
              <el-switch style="margin-left: 2%;" v-model="form.sslStatus" active-color="#13ce66"
                inactive-color="#ff4949">
              </el-switch>
              <el-alert title="SSL开启则会自动跳转https，反之则跳转http" type="warning" :closable="false">
              </el-alert>
            </el-form-item> 

        <el-form-item label="域名名称" prop="name">
          <el-input
            v-model="form.name"
            type="textarea"
            placeholder="请输入域名名称"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
  
  <script>
import { domainList,updateDomain,delDomain,addDomain } from "@/api/shortUrl/domain";

export default {
  name: "Dict",
  dicts: ["sys_normal_disable"],
  data() {
    return {
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
        pageSize: 10,
      },
      queryBody: {
        groupName: undefined,
      },

      // 列信息
      columns: [
        { key: 0, label: `序号`, visible: true },
        { key: 1, label: `域名`, visible: true },
        { key: 2, label: `名称`, visible: true },
        { key: 3, label: `是否SSL`, visible: true },
        { key: 4, label: `创建时间`, visible: true },
      ],

      // 表单参数
      form: {},
      // 表单校验
      rules: {
          domain: [
          { required: true, message: "域名不能为空", trigger: "blur" },
        ],
        name: [
          { required: true, message: "域名名称不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getCurrentIndex(pageNum, pageSize) {
      return pageSize * (pageNum - 1) + 1;
    },

    /** 查询分组列表 */
    getList() {
      this.loading = true;
      domainList(this.queryParams)
        .then((response) => {
          this.typeList = response.rows;
          this.total = response.total;
          this.loading = false;
        })
        .catch((error) => {
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
        groupName: undefined,
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
      this.title = "添加域名";
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();

      const id = row.id || this.ids;
      const typeListIndex = this.typeList.findIndex((item) => item.id == id);
      if (id !== -1) {
        this.form = Object.assign({}, this.typeList[typeListIndex]);
        this.open = true;
        this.title = "修改域名";
      }
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateDomain(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDomain(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
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
        idList: idList,
      };
      this.$modal
        .confirm("域名删除将无法恢复，是否确认删除？")
        .then(function () {
          return delDomain(param);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },

    handleRefreshCache() {
      refreshCache().then(() => {
        this.$modal.msgSuccess("刷新成功");
        this.$store.dispatch("dict/cleanDict");
      });
    },
  },
};
</script>