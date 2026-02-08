<template>
  <div class="login-container" ref="login" @keyup.enter="handleLogin">
    <div class="login-weaper animated bounceInDown">
      <div class="login-border">
        <div class="login-main">
          <h4 class="login-title">
            <img class="login-xxzg-icon" src="/img/bg/xxzg.png" alt="xxzg"/>
            {{ $t('login.title') }}{{ website.title }}
          </h4>
          <userLogin v-if="activeName === 'user'"></userLogin>
          <codeLogin v-else-if="activeName === 'code'"></codeLogin>
          <thirdLogin v-else-if="activeName === 'third'"></thirdLogin>
          <div class="login-menu">
            <a href="#" @click.stop="activeName = 'user'">{{ $t('login.msgInfo') }}</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import userLogin from './xxclouduserlogin.vue';
import codeLogin from './codelogin.vue';
import thirdLogin from './thirdlogin.vue';
import { mapGetters } from 'vuex';
import { validatenull } from '@/utils/validate';
import topLang from '@/page/index/top/top-lang.vue';
import { getQueryString, getTopUrl } from '@/utils/util';
import website from '@/config/website';

export default {
  name: 'login',
  components: {
    userLogin,
    codeLogin,
    thirdLogin,
    topLang,
  },
  data() {
    return {
      website: website,
      time: '',
      activeName: 'user',
      socialForm: {
        tenantId: '000000',
        source: '',
        code: '',
        state: '',
      },
    };
  },
  watch: {
    $route() {
      this.handleLogin();
    },
  },
  created() {
    this.handleLogin();
    this.getTime();
  },
  mounted() {},
  computed: {
    ...mapGetters(['tagWel']),
  },
  props: [],
  methods: {
    getTime() {
      setInterval(() => {
        this.time = this.$dayjs().format('YYYY-MM-DD HH:mm:ss');
      }, 1000);
    },
    handleLogin() {
      const topUrl = getTopUrl();
      const redirectUrl = '/oauth/redirect/';
      const ssoCode = '?code=';
      this.socialForm.source = getQueryString('source');
      this.socialForm.code = getQueryString('code');
      this.socialForm.state = getQueryString('state');
      if (validatenull(this.socialForm.source) && topUrl.includes(redirectUrl)) {
        let source = topUrl.split('?')[0];
        source = source.split(redirectUrl)[1];
        this.socialForm.source = source;
      }
      if (
        topUrl.includes(redirectUrl) &&
        !validatenull(this.socialForm.source) &&
        !validatenull(this.socialForm.code) &&
        !validatenull(this.socialForm.state)
      ) {
        const loading = this.$loading({
          lock: true,
          text: '第三方系统登录中,请稍后',
          background: 'rgba(0, 0, 0, 0.7)',
        });
        this.$store
          .dispatch('LoginBySocial', this.socialForm)
          .then(() => {
            window.location.href = topUrl.split(redirectUrl)[0];
            //加载工作流路由集
            this.loadFlowRoutes();
            this.$router.push(this.tagWel);
            loading.close();
          })
          .catch(() => {
            loading.close();
          });
      } else if (
        !topUrl.includes(redirectUrl) &&
        !validatenull(this.socialForm.code) &&
        !validatenull(this.socialForm.state)
      ) {
        const loading = this.$loading({
          lock: true,
          text: '单点系统登录中,请稍后',
          background: 'rgba(0, 0, 0, 0.7)',
        });
        this.$store
          .dispatch('LoginBySso', this.socialForm)
          .then(() => {
            window.location.href = topUrl.split(ssoCode)[0];
            //加载工作流路由集
            this.loadFlowRoutes();
            this.$router.push(this.tagWel);
            loading.close();
          })
          .catch(() => {
            loading.close();
          });
      }
    },
    loadFlowRoutes() {
      this.$store.dispatch('FlowRoutes').then(() => {});
    },
  },
};
</script>

<style lang="scss">
@import '@/styles/xxcloudlogin.scss';
</style>
