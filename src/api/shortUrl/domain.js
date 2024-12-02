import request from '@/utils/request'

// 查询域名列表
export function domainList(query) {
  return request({
    url: '/domain/list',
    method: 'post',
    params: query,
    data: null
  })
}


// 新增域名
export function addDomain(data) {
    return request({
      url: '/domain/add',
      method: 'post',
      data: data
    })
  }
  

// 删除域名
export function delDomain(data) {
    return request({
      url: '/domain/del',
      method: 'post',
      data: data
    })
  }
  


// 修改域名
export function updateDomain(data) {
    return request({
      url: '/domain/update',
      method: 'post',
      data: data
    })
  }
    

  // 获取所有域名
export function getDomainAll() {
    return request({
      url: '/domain/getDomainAll',
      method: 'post',
      data: null
    })
  }
    