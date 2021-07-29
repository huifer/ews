import request from '@/utils/request'

export function processQuery(params) {
  return request({
    url: '/process/query',
    method: 'get',
    params
  })
}

export function processPTrue(params) {
  return request({
    url: '/process/p_true',
    method: 'get',
    params
  })
}


export function processPFalse(params) {
  return request({
    url: '/process/p_false',
    method: 'get',
    params
  })
}


export function processAdd(params) {
  return request({
    url: '/process/add',
    method: 'post',
    data: params
  })
}
