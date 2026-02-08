export default {
  tabs: true,
  tabsActive: 1,
  group: [
    {
      label: '个人信息',
      prop: 'info',
      column: [
        {
          label: '头像',
          type: 'upload',
          listType: 'picture-img',
          propsHttp: {
            res: 'data',
            url: 'link',
          },
          action: '/blade-resource/oss/endpoint/put-file',
          tip: '只能上传jpg/png用户头像，且不超过500kb',
          span: 12,
          row: true,
          prop: 'avatar',
        },
        {
          prefixIcon: 'el-icon-bell',
          label: '姓名',
          span: 12,
          row: true,
          prop: 'realName',
        },
        {
          prefixIcon: 'el-icon-user',
          label: '用户名',
          span: 12,
          row: true,
          prop: 'name',
        },
        {
          prefixIcon: 'el-icon-iphone',
          label: '手机号',
          span: 12,
          row: true,
          prop: 'phone',
        },
        {
          prefixIcon: 'el-icon-message',
          label: '邮箱',
          prop: 'email',
          span: 12,
          row: true,
        },
      ],
    },
    {
      label: '修改密码',
      prop: 'password',
      column: [
        {
          prefixIcon: 'el-icon-lock',
          label: '原密码',
          span: 12,
          row: true,
          type: 'password',
          prop: 'oldPassword',
        },
        {
          prefixIcon: 'el-icon-lock',
          label: '新密码',
          span: 12,
          row: true,
          type: 'password',
          prop: 'newPassword',
        },
        {
          prefixIcon: 'el-icon-lock',
          label: '确认密码',
          span: 12,
          row: true,
          type: 'password',
          prop: 'newPassword1',
        },
      ],
    },
  ],
};
