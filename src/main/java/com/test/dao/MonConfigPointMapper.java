package com.test.dao;

import com.test.entity.MonConfigPoint;
import org.springframework.stereotype.Repository;

@Repository
public interface MonConfigPointMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mon_configuration_point
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mon_configuration_point
     *
     * @mbggenerated
     */
    int insert(MonConfigPoint record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mon_configuration_point
     *
     * @mbggenerated
     */
    int insertSelective(MonConfigPoint record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mon_configuration_point
     *
     * @mbggenerated
     */
    MonConfigPoint selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mon_configuration_point
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(MonConfigPoint record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mon_configuration_point
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(MonConfigPoint record);
}