<script setup>

</script>

<template>
  <router-view v-slot="{ Component }">
    <transition name="fade" mode="out-in" appear>
      <Suspense>
        <keep-alive>
          <component :is="Component" />
        </keep-alive>
      </Suspense>
    </transition>
  </router-view>
</template>

<style scoped lang="scss">
html,body{
  margin: 0;
  height: 100%;
  width: 100%;
}
.fade-enter-active, .fade-leave-active {
  transition: opacity 1s ease;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
}
</style>
