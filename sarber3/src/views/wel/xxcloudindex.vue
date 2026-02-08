<template>
  <div>
    <iframe src="http://it.xinxing-pipes.com:8062" frameborder="0" scrolling="no" style="width: 100%; height: 3100px"></iframe>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  name: 'wel',
  data() {
    return {
      activeNames: ['1', '2', '3', '5'],
      logActiveNames: ['38'],
    };
  },
  computed: {
    ...mapGetters(['userInfo']),
  },
  methods: {
    handleChange(val) {
      window.console.log(val);
    },
  },
};
</script>

<style>
.el-font-size {
  font-size: 14px;
}
</style>
