

// 短网址跳转
export function jumpUrl() {
    return request({
      url: '/openUrl',
      method: 'get',
    })
  }
  