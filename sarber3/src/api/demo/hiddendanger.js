import request from '@/axios';

export const getList = (current, size, params) => {
  return request({
    url: '/xinxing-demo/hiddendanger/list',
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
    url: '/xinxing-demo/hiddendanger/detail',
    method: 'get',
    params: {
      id
    }
  })
}

export const remove = (ids) => {
  return request({
    url: '/xinxing-demo/hiddendanger/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const add = (row) => {
  return request({
    url: '/xinxing-demo/hiddendanger/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/xinxing-demo/hiddendanger/submit',
    method: 'post',
    data: row
  })
}

