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
