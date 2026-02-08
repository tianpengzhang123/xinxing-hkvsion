<template>
  <div>
    <div v-if = isErpShow>
      <basic-container>
        <div class="erpPage" @click="reload">
          <h1>此页面为外部系统页面，已在外部链接打开，点击可重新打开</h1>
          <img src="/img/bg/vip3.png" alt="vip3" style="margin-top: 20px"/>
        </div>
      </basic-container>
    </div>
    <div style="margin-top: 220px" v-if = is403Show>
      <error403></error403>
    </div>
  </div>
</template>

<script>
import crypto from "@/utils/crypto";
import {getKey} from "@/api/system/erpkey";
import BasicContainer from "@/components/basic-container/main.vue";
import Error403 from "@/components/error-page/403.vue";

export default {
  name: 'ErpOpen',
  components: {Error403, BasicContainer},
  data() {
    return {
      safeUrl: '',
      isErpShow: true,
      is403Show: false,
    };
  },
  created: function () {
    this.safeUrl = crypto.decrypt(this.$route.query.url);
    this.reload();
  },
  methods: {
    reload() {
      getKey(this.safeUrl).then(res => {
        let responseInfo = res.data.data;
        if (responseInfo === '403') {
          this.isErpShow = false;
          this.is403Show = true;
        } else {
          window.open(responseInfo, '_blank');
          this.is403Show = false;
          this.isErpShow = true;
        }
      });
    }
  }
};
</script>

<style lang="scss">
.erpPage {
  margin-top: 60px;
  text-align: center;
  width: 100%;
  height: 720px;
  border: 0;
  overflow: hidden;
  box-sizing: border-box;
  cursor: pointer;
}
</style>
