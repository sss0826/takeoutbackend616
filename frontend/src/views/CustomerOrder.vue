<template>
  <div>
    <h2>🍽️ 点餐</h2>
    <p>当前门店：快乐炸鸡店</p>
    
    <el-card v-for="dish in dishList" :key="dish.id" style="margin-bottom: 15px">
      <div style="display: flex; justify-content: space-between; align-items: center">
        <div>
          <h3>{{ dish.name }}</h3>
          <p>价格：¥{{ dish.price }} | 月销：{{ dish.sales }}</p>
        </div>
        <el-button type="primary" size="small" @click="addToCart(dish)">加入购物车</el-button>
      </div>
    </el-card>

    <el-button type="success" @click="goCart" style="margin-top: 20px">去购物车结算</el-button>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()
const dishList = ref([
  { id: 1, name: '香辣鸡腿堡', price: 18, sales: 999 },
  { id: 2, name: '薯条', price: 10, sales: 888 },
  { id: 3, name: '可乐', price: 6, sales: 777 },
  { id: 4, name: '鸡米花', price: 12, sales: 666 }
])

const addToCart = (dish) => {
  ElMessage.success(`已添加 ${dish.name} 到购物车`)
}

const goCart = () => {
  router.push('/customer/cart')
}
</script>