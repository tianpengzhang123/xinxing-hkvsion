import request from '@/axios';

export const getList = (current, size, params) => {
  return request({
    url: '/xinxing-demo/org/list',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}

export const getDetail = (id) => {
  return request({
    url: '/xinxing-demo/org/detail',
    method: 'get',
    params: {
      id
    }
  })
}

export const getTree = (tenantId) => {
  return request({
    url: '/xinxing-demo/org/tree',
    method: 'get',
    params: {
      tenantId,
    }
  })
}

export const remove = (ids) => {
  return request({
    url: '/xinxing-demo/org/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const add = (row) => {
  return request({
    url: '/xinxing-demo/org/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/xinxing-demo/org/submit',
    method: 'post',
    data: row
  })
}

