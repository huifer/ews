<template>
  <div>
    <div id="rule_query">
      <el-form
        :model="rule_query"
        class="form"
        label-position="right"
        label-width="120px"
      >
        <el-row>
          <el-col :span="8">
            <el-form-item label="场景名称" prop="name">
              <el-input v-model="rule_query.sceneName" placeholder="请输入"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="规则名称" prop="description">
              <el-input v-model="rule_query.name" placeholder="请输入"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-button circle icon="el-icon-search" type="primary" @click="queryRule"></el-button>
            <el-button type="primary" @click="dialogVisible_rule_create = true">新增</el-button>
          </el-col>

        </el-row>
      </el-form>
    </div>

    <div id="show_table">
      <el-table
        :data="tableData"
        style="width: 100%"
      >
        <el-table-column
          label="场景名称"
          prop="sceneName"
          width="180"
        >
        </el-table-column>
        <el-table-column
          label="规则名称"
          prop="name"
          width="180"
        >
        </el-table-column>
        <el-table-column
          label="取值表达式"
          prop="expression"
          width="180"
        >
        </el-table-column>
        <el-table-column
          label="运算符"
          prop="opStr"
          width="180"
        >
        </el-table-column>
        <el-table-column
          label="比较值"
          prop="comparisonValue"
        >
        </el-table-column>
      </el-table>
    </div>

    <div>
      <el-dialog
        :before-close="handleClose"
        :visible.sync="dialogVisible_rule_create"
        title="创建规则"
        width="30%"
      >
        <el-form :model="rule_create">
          <el-form-item :label-width="name" label="规则名称">
            <el-input v-model="rule_create.name" autocomplete="off"></el-input>
          </el-form-item>
          <!--          场景列表查询-->
          <el-form-item :label-width="name" label="场景名称">
            <el-select v-model="rule_create.scenesId" placeholder="请选择">
              <el-option
                v-for="item in scenes_id"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item :label-width="expression" label="值提取式">
            <el-input v-model="rule_create.expression" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item :label-width="operator" label="运算符">
            <el-select v-model="rule_create.operator" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item :label-width="comparisonValue" label="比较值">
            <el-input v-model="rule_create.comparisonValue" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible_rule_create = false">取 消</el-button>
          <el-button type="primary" @click="rule_create_api">确 定</el-button>
        </div>
      </el-dialog>

    </div>
  </div>
</template>

<script>
import { ruleAdd, ruleQuery } from '@/api/rule'
import { scenesList } from '@/api/scenes'

export default {
  name: 'Rule',
  data() {
    return {
      rule_query: {
        sceneName: '',
        sceneId: '',
        name: '',
        pageSize: 10,
        pageNumber: 0
      },
      total: 0,
      tableData: [],
      dialogVisible_rule_create: false,
      rule_create: {
        scenesId: '',
        expression: '',
        comparisonValue: '',
        operator: '',
        name: ''
      },
      options: [{
        value: '0',
        label: '等于'
      }, {
        value: '1',
        label: '不等于'
      }, {
        value: '2',
        label: '大于等于'
      }, {
        value: '3',
        label: '大于'
      }, {
        value: '4',
        label: '小于'
      }, {
        value: '5',
        label: '小于等于'
      }],
      scenes_id: []
    }
  },
  methods: {
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {
        })
    },
    rule_create_api() {
      this.dialogVisible_rule_create = false
      console.log(this.rule_create)
      ruleAdd({
        scenesId: this.rule_create.scenesId,
        expression: this.rule_create.expression,
        comparisonValue: this.rule_create.comparisonValue,
        operator: this.rule_create.operator,
        name: this.rule_create.name
      })
      this.queryRule()
    },
    queryRule() {
      ruleQuery({
        sceneName: this.rule_query.sceneName,
        sceneId: this.rule_query.sceneId,
        name: this.rule_query.name,
        pageSize: 10,
        pageNumber: 0
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
      }).catch(e => {
        console.log(e)
      })
    },
    init_scenes() {
      scenesList().then(
        res => {
          this.scenes_id = res.data
        }
      )
    }
  },
  created() {
    this.queryRule()
    this.init_scenes()

  }
}
</script>

<style scoped>

</style>
