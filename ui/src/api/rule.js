import request from '@/utils/request'

export function ruleQuery(params) {
  return request({
    url: '/rule/query',
    method: 'get',
    params
  })
}

export function ruleAdd(data) {
  return request({
    url: '/rule/add',
    method: 'post',
    data: data
  })
}

export function ruleIds(params) {
  return request({
    url: '/rule/ids',
    method: 'get',
    params
  })
}

export function ruleExp(params) {
  return request({
    url: '/rule/exp',
    method: 'get',
    params
  })
}

export function ruleScId(params) {
  return request({
    url: '/rule/scId',
    method: 'get',
    params
  })
}
