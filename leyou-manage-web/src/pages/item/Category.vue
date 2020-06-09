<template>
  <v-card>
      <v-flex xs12 sm10>
        <v-tree url="/item/category/list"
                :isEdit="isEdit"
                @handleAdd="handleAdd"
                @handleEdit="handleEdit"
                @handleDelete="handleDelete"
                @handleClick="handleClick"
        />
      </v-flex>
  </v-card>
</template>

<script>

  export default {
    name: "category",
    data() {
      return {
        isEdit:true,
      }
    },
    methods: {
      handleAdd(node) {
        console.log("add .... ");
        console.log(node);
        // this.$http.post("/item/category/add",
        //     node
        //   ).then((res)=>{
        //     if(res.data=="SUCC"){
        //       alert("添加成功")
        //     }else if(res.data=="FAIL"){
        //       alert("添加失败")
        //     }
        //     window.location.reload();
        //   }).catch((err)=>{
        //     alert("出现错误")
        //   });

      },
      handleEdit(node) {
        //console.log("edit... id: " + id + ", name: " + name)


        console.log("------------------------------");
        console.log(node);
        console.log("------------------------------");

        let id=node.id;
        if(id==0){

          this.$http.post("/item/category/add",
            node
          ).then((res)=>{
            if(res.data=="SUCC"){
              alert("添加成功")
            }else if(res.data=="FAIL"){
              alert("添加失败")
            }
            window.location.reload();
          }).catch((err)=>{
            alert("出现错误")
          });
        }else {
          this.$http.post("/item/category/update", node).then((res) => {
            if (res.data == "SUCC") {
              alert("修改成功")
            } else if (res.data == "FAIL") {
              alert("修改失败")
            }
            window.location.reload();
          }).catch((err) => {
            alert("出现错误")
          });
        }


      },
      handleDelete(id) {
        console.log("delete ... " + id)

        this.$http.get("/item/category/deleteById",{
              params:{
                id:id
              }
          }

        ).then((res)=>{
          if(res.data=="SUCC"){
              alert("删除成功")
          }else if(res.data=="FAIL"){
              alert("删除失败")
          }
        }).catch((err)=>{
            alert("出现错误")
        })

      },
      handleClick(node) {
        console.log(node)
      }
    }
  };
</script>

<style scoped>

</style>
