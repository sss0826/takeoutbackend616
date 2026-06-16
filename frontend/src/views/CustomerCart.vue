<template>
  <div>
    <h2>🛒 购物车</h2>
    
    <el-table :data="cartList" style="width: 100%">
      <el-table-column prop="name" label="菜品名称" />
      <el-table-column prop="price" label="单价" />
      <el-table-column label="数量">
        <template #default="{ row }">
          <el-input-number v-model="row.quantity" :min="1" size="small" />
        </template>
      </el-table-column>
      <el-table-column label="小计">
        <template #default="{ row }">
          ¥{{ row.price * row.quantity }}
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button type="danger" size="small" @click="removeItem(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top: 20px; text-align: right">
      <h3>总计：¥{{ totalPrice }}</h3>
      <el-button type="primary" @click="checkout">去结算</el-button>
      <el-button @click="continueOrder">继续点餐</el-button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()
const cartList = ref([
  { id: 1, name: '香辣鸡腿堡', price: 18, quantity: 2 },
  { id: 2, name: '薯条', price: 10, quantity: 1 }
])

const totalPrice = computed(() => {
  return cartList.value.reduce((sum, item) => sum + item.price * item.quantity, 0)
})

const removeItem = (item) => {
  const index = cartList.value.indexOf(item)
  cartList.value.splice(index, 1)
  ElMessage.success('已删除')
}

const checkout = () => {
  ElMessage.success('模拟支付成功！订单已提交')
  router.push('/customer/home')
}

const continueOrder = () => {
  router.push('/customer/order')
}
</script>