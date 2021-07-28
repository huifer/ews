<template>
  <div>{{ from_parent }}</div>
</template>

<script>
export default {
  name: 'ShowActionParam',
  props: {
    'from_parent': String
  },
  data() {
    return {
      // eslint-disable-next-line vue/no-dupe-keys
      from_parent: ''
    }
  },
  created() {
    console.log(this.from_parent)
  }
}
</script>

<style scoped>

</style>
