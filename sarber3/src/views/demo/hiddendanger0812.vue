<template>
  <basic-container>
    <avue-crud :option="option"
               v-model:search="search"
               v-model:page="page"
               v-model="form"
               :table-loading="loading"
               :data="data"
               :permission="permissionList"
               :before-open="beforeOpen"
               ref="crud"
               @row-update="rowUpdate"
               @row-save="rowSave"
               @row-del="rowDel"
               @search-change="searchChange"
               @search-reset="searchReset"
               @selection-change="selectionChange"
               @current-change="currentChange"
               @size-change="sizeChange"
               @refresh-change="refreshChange"
               @on-load="onLoad">
      <template #menu-left>
        <el-button type="danger"
                   icon="el-icon-delete"
                   plain
                   v-if="permission.hiddendanger0812_delete"
                   @click="handleDelete">删 除
        </el-button>
        <el-button type="warning"
                   plain
                   icon="el-icon-download"
                   @click="handleExport">导 出
        </el-button>
      </template>
      <template #menu="row">
        <el-button type="text"
                   icon="el-icon-setting"
                   @click.stop="handleDataSub(row)">配 置
        </el-button>
      </template>
    </avue-crud>
    <el-drawer :title="`[${hiddendanger0812Name}] 配置`" v-model="subVisible" :direction="direction" append-to-body
               :before-close="handleSubClose" size="1000px">
      <basic-container>
        <avue-crud :option="optionSub"
                   :data="dataSub"
                   v-model:page="pageSub"
                   v-model="formSub"
                   :table-loading="loadingSub"
                   ref="crudSub"
                   @row-del="rowDelSub"
                   @row-update="rowUpdateSub"
                   @row-save="rowSaveSub"
                   :before-open="beforeOpenSub"
                   @search-change="searchChangeSub"
                   @search-reset="searchResetSub"
                   @selection-change="selectionChangeSub"
                   @current-change="currentChangeSub"
                   @size-change="sizeChangeSub"
                   @on-load="onLoadSub">
          <template #menu-left>
            <el-button type="danger"
                       icon="el-icon-delete"
                       plain
                       @click="handleDeleteSub">删 除
            </el-button>
          </template>
        </avue-crud>
      </basic-container>
    </el-drawer>
  </basic-container>
</template>

<script>
  import {getList, getDetail, add, update, remove} from "@/api/demo/hiddendanger0812";
  import {getList as getListSub, getDetail as getDetailSub, add as addSub, update as updateSub, remove as removeSub} from "@/api/demo/hiddendanger0812Log";
  import option from "@/option/demo/hiddendanger0812";
  import optionSub from "@/option/demo/hiddendanger0812Log";
  import {mapGetters} from "vuex";
  import {exportBlob} from "@/api/common";
  import {getToken} from '@/utils/auth';
  import {downloadXls} from "@/utils/util";
  import {dateNow} from "@/utils/date";
  import NProgress from 'nprogress';
  import 'nprogress/nprogress.css';

  export default {
    data() {
      return {
        form: {},
        query: {},
        search: {},
        loading: true,
        data: [],
        selectionList: [],
        page: {
          pageSize: 10,
          currentPage: 1,
          total: 0
        },
        option: option,
        subVisible: false,
        direction: 'rtl',
        mainId : 0,
        hiddendanger0812Name: " train_hiddendanger0812 ",
        formSub: {},
        querySub: {},
        loadingSub: true,
        dataSub: [],
        selectionListSub: [],
        pageSub: {
          pageSize: 10,
          currentPage: 1,
          total: 0
        },
        optionSub: optionSub
      };
    },
    computed: {
      ...mapGetters(["permission"]),
      permissionList() {
        return {
          addBtn: this.validData(this.permission.hiddendanger0812_add, false),
          viewBtn: this.validData(this.permission.hiddendanger0812_view, false),
          delBtn: this.validData(this.permission.hiddendanger0812_delete, false),
          editBtn: this.validData(this.permission.hiddendanger0812_edit, false)
        };
      },
      ids() {
        let ids = [];
        this.selectionList.forEach(ele => {
          ids.push(ele.id);
        });
        return ids.join(",");
      },
      subIds() {
        let ids = [];
        this.selectionListSub.forEach(ele => {
          ids.push(ele.id);
        });
        return ids.join(",");
      }
    },
    methods: {
      // 主表模块
      rowSave(row, done, loading) {
        add(row).then(() => {
          this.onLoad(this.page);
          this.$message({
            type: "success",
            message: "操作成功!"
          });
          done();
        }, error => {
          window.console.log(error);
          loading();
        });
      },
      rowUpdate(row, index, done, loading) {
        update(row).then(() => {
          this.onLoad(this.page);
          this.$message({
            type: "success",
            message: "操作成功!"
          });
          done();
        }, error => {
          window.console.log(error);
          loading();
        });
      },
      rowDel(row) {
        this.$confirm("确定将选择数据删除?", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            return remove(row.id);
          })
          .then(() => {
            this.onLoad(this.page);
            this.$message({
              type: "success",
              message: "操作成功!"
            });
          });
      },
      handleDelete() {
        if (this.selectionList.length === 0) {
          this.$message.warning("请选择至少一条数据");
          return;
        }
        this.$confirm("确定将选择数据删除?", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            return remove(this.ids);
          })
          .then(() => {
            this.onLoad(this.page);
            this.$message({
              type: "success",
              message: "操作成功!"
            });
            this.$refs.crud.toggleSelection();
          });
      },
      handleExport() {
        let downloadUrl = `/xinxing-demo/hiddendanger0812/export-hiddendanger0812?${this.website.tokenHeader}=${getToken()}`;
        const {
            hiddenDangerDesc,
            hiddenDangerType,
         } = this.query;
        let values = {
             hiddenDangerDesc_like: hiddenDangerDesc,
             hiddenDangerType_like: hiddenDangerType,
        };
        this.$confirm("是否导出数据?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          NProgress.start();
          exportBlob(downloadUrl, values).then(res => {
            downloadXls(res.data, ` train_hiddendanger0812 ${dateNow()}.xlsx`);
            NProgress.done();
          })
        });
      },
      beforeOpen(done, type) {
        if (["edit", "view"].includes(type)) {
          getDetail(this.form.id).then(res => {
            this.form = res.data.data;
          });
        }
        done();
      },
      searchReset() {
        this.query = {};
        this.onLoad(this.page);
      },
      searchChange(params, done) {
        this.query = params;
        this.page.currentPage = 1
        this.onLoad(this.page, params);
        done();
      },
      selectionChange(list) {
        this.selectionList = list;
      },
      selectionClear() {
        this.selectionList = [];
        this.$refs.crud.toggleSelection();
      },
      currentChange(currentPage){
        this.page.currentPage = currentPage;
      },
      sizeChange(pageSize){
        this.page.pageSize = pageSize;
      },
      onLoad(page, params = {}) {
        this.loading = true;
        const {
            hiddenDangerDesc,
            hiddenDangerType,
        } = this.query;

        let values = {
            hiddenDangerDesc_like: hiddenDangerDesc,
            hiddenDangerType_like: hiddenDangerType,
        };
        getList(page.currentPage, page.pageSize, values).then(res => {
          const data = res.data.data;
          this.page.total = data.total;
          this.data = data.records;
          this.loading = false;
          this.selectionClear();
        });
      },
      // 子表模块
      handleDataSub(row) {
        this.subVisible = true;
        this.mainId  = row.id;
        this.onLoadSub(this.pageSub)
      },
      handleSubClose(hide) {
        hide();
      },
      rowSaveSub(row, loading, done) {
        row = {
          ...row,
          mainId : this.mainId ,
        };
        addSub(row).then(() => {
          loading();
          this.onLoadSub(this.pageSub);
          this.$message({
            type: "success",
            message: "操作成功!"
          });
        }, error => {
          done();
          window.console.log(error);
        });
      },
      rowUpdateSub(row, index, loading, done) {
        row = {
          ...row,
          mainId : this.mainId ,
        };
        updateSub(row).then(() => {
          loading();
          this.onLoadSub(this.pageSub);
          this.$message({
            type: "success",
            message: "操作成功!"
          });
        }, error => {
          done();
          window.console.log(error);
        });
      },
      rowDelSub(row) {
        this.$confirm("确定将选择数据删除?", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            return removeSub(row.id);
          })
          .then(() => {
            this.onLoadSub(this.pageSub);
            this.$message({
              type: "success",
              message: "操作成功!"
            });
          });
      },
      handleDeleteSub() {
        if (this.selectionListSub.length === 0) {
          this.$message.warning("请选择至少一条数据");
          return;
        }
        this.$confirm("确定将选择数据删除?", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            return removeSub(this.subIds);
          })
          .then(() => {
            this.onLoadSub(this.pageSub);
            this.$message({
              type: "success",
              message: "操作成功!"
            });
            this.$refs.crudSub.toggleSelection();
          });
      },
      beforeOpenSub(done, type) {
        if (["edit", "view"].includes(type)) {
          getDetailSub(this.formSub.id).then(res => {
            this.formSub = res.data.data;
          });
        }
        done();
      },
      searchResetSub() {
        this.querySub = {};
        this.onLoadSub(this.pageSub);
      },
      searchChangeSub(params) {
        this.querySub = params;
        this.onLoadSub(this.pageSub, params);
      },
      selectionChangeSub(list) {
        this.selectionListSub = list;
      },
      currentChangeSub(currentPage) {
        this.pageSub.currentPage = currentPage;
      },
      sizeChangeSub(pageSize) {
        this.pageSub.pageSize = pageSize;
      },
      refreshChange() {
        this.onLoad(this.page, this.query);
      },
      onLoadSub(page, params = {}) {
        this.loadingSub = true;

        let values = {
          mainId : this.mainId ,
        }

        const {
          mainId,
          operationType,
        } = this.querySub;

        values = {
        ...values,
          mainId_like: mainId,
          operationType_like: operationType,
        };

        getListSub(page.currentPage, page.pageSize, values).then(res => {
          const data = res.data.data;
          this.pageSub.total = data.total;
          this.dataSub = data.records;
          this.selectionListSub = [];
          this.loadingSub = false;
        });
      },
    }
  };
</script>

<style>
</style>
