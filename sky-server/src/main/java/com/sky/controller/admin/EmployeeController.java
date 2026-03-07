package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@Api(tags = "Employtee related API")
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @ApiOperation("Employee login API")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation("Employee logout API")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }
    /**
     *add employee
     *
     * @return
     */
    @ApiOperation("Employee add API")
    @PostMapping
    public Result addEmp(@RequestBody EmployeeDTO dto)
    {

        log.info("add Employee：{}", dto);

        //no return value
        employeeService.addEmp(dto);

        return Result.success();
    }

    /**
     * page search
     * @param dto
     * @return
     */
    @ApiOperation("Employee page search API")
    @GetMapping("/page")
    public Result<PageResult> page(EmployeePageQueryDTO dto)
    {

        log.info("Employee page search：{}", dto);

        //no return value
        PageResult result =  employeeService.page(dto);

        return Result.success(result);
    }


    @ApiOperation("Employee enable or disable API")
    @PostMapping("/status/{status}")
    public Result enableOrDisable(@PathVariable Integer status, Long id)
    {

        log.info("Employee status id：{}", status , id);


        employeeService.enableOrDisable(status, id);

        return Result.success();
    }
    @ApiOperation("Get Employee by id API")
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id)
    {

        log.info("Employee id：{}", id);


        Employee employee =  employeeService.getById(id);

        return Result.success(employee);
    }

    @ApiOperation("Edit Employee API")
    @PutMapping
    public Result<Employee> update(@RequestBody EmployeeDTO dto)
    {

        log.info("Update Employee{}", dto);


        employeeService.update(dto);

        return Result.success();
    }






}
