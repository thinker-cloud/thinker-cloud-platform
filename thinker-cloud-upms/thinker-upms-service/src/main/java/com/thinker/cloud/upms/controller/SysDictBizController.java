package com.thinker.cloud.upms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thinker.cloud.core.model.Result;
import com.thinker.cloud.core.model.vo.PageVO;
import com.thinker.cloud.upms.api.sys.model.dto.SysDictBizDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysDictBizQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysDictBizVO;
import com.thinker.cloud.upms.service.ISysDictBizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 业务字典
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Tag(name = "SysDictBizController", description = "业务字典")
@RestController
@AllArgsConstructor
@RequestMapping("sys/dict/biz")
public class SysDictBizController {

    private final ISysDictBizService sysDictBizService;

    @PostMapping(value = "page")
    @Operation(summary = "分页列表", description = "传入page与limit可分页查询")
    public Result<PageVO<SysDictBizVO>> page(@RequestBody SysDictBizQuery query) {
        IPage<SysDictBizVO> page = query.generatePage();
        sysDictBizService.page(page, query);
        return Result.success(new PageVO<>(page));
    }

    @PostMapping(value = "list")
    @Operation(summary = "不分页列表", description = "所有数据列表查询")
    public Result<List<SysDictBizVO>> list(@RequestBody SysDictBizQuery query) {
        return Result.success(sysDictBizService.list(query));
    }

    @GetMapping(value = "detail/{id}")
    @Operation(summary = "根据id查询")
    public Result<SysDictBizVO> detail(@PathVariable Long id) {
        return Result.success(sysDictBizService.findDetail(id));
    }

    @PostMapping
    @Operation(summary = "新增数据")
    public Result<Boolean> saveData(@RequestBody @Valid SysDictBizDTO dto) {
        return Result.success(sysDictBizService.saveData(dto));
    }

    @PutMapping
    @Operation(summary = "修改数据", description = "根据id修改数据")
    public Result<Boolean> modifyById(@RequestBody @Valid SysDictBizDTO dto) {
        return Result.success(sysDictBizService.modifyById(dto));
    }

    @DeleteMapping(value = "{id}")
    @Operation(summary = "删除数据", description = "根据id删除数据")
    public Result<Boolean> removeById(@PathVariable Long id) {
        return Result.success(sysDictBizService.removeById(id));
    }
}
