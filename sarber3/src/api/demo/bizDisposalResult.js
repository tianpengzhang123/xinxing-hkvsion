import request from '@/axios';

export const getList = (current, size, params) => {
  return request({
    url: '/xinxing-demo/bizDisposalResult/list',
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
    url: '/xinxing-demo/bizDisposalResult/detail',
    method: 'get',
    params: {
      id
    }
  })
}

export const remove = (ids) => {
  return request({
    url: '/xinxing-demo/bizDisposalResult/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const add = (row) => {
  return request({
    url: '/xinxing-demo/bizDisposalResult/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/xinxing-demo/bizDisposalResult/submit',
    method: 'post',
    data: row
  })
}

