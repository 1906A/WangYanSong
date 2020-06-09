import Vue from 'vue'
import Router from 'vue-router'


Vue.use(Router)

function route (path, file, name, children) {
  return {
    exact: true,
    path,
    name,
    children,
    component: () => import('../pages' + file)
  }
}

export default new Router({
  routes: [
    route("/login",'/Login',"Login"),// /login路径，路由到登录组件
    {
      path:"/", // 根路径，路由到 Layout组件
      component: () => import('../pages/Layout'),
      redirect:"/index/dashboard",
      children:[ // 其它所有组件都是 Layout的子组件
        route("/index/dashboard","/Dashboard","Dashboard"),//首页-->统计页面
        route("/item/category",'/item/Category',"Category"),//商品管理-->分类管理
        route("/item/brand",'/item/Brand',"Brand"),//商品管理-->品牌
        route("/item/list",'/item/Goods',"Goods"),//商品管理-->商品列表
        route("/item/specification",'/item/specification/Specification',"Specification"),//商品管理-->规格参数
        route("/user/statistics",'/item/Statistics',"Statistics"),
        route("/trade/promotion",'/trade/Promotion',"Promotion")//销售管理-->促销管理
      ]
    }
  ]
})
