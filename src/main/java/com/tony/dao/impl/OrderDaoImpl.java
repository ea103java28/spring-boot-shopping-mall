//package com.tony.dao.impl;
//
//import com.tony.dto.OrderQueryParams;
//import com.tony.model.Order;
//import com.tony.model.OrderItem;
//import com.tony.rowmapper.OrderItemRowMapper;
//import com.tony.rowmapper.OrderRowMapper;
//import com.tony.dao.OrderDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Repository;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Repository
//public class OrderDaoImpl implements OrderDao {
//
//    @Autowired
//    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//
//    @Override
//    public Integer countOrder(OrderQueryParams orderQueryParams) {
//        String sql = "SELECT count(*) FROM `order` WHERE 1 = 1";
//
//        Map<String, Object> map = new HashMap<>();
//
//        // 查詢條件
//        sql = addFilteringSql(sql, map, orderQueryParams);
//
//        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
//
//        return total;
//    }
//
//    @Override
//    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
//        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date " +
//                "FROM `order` WHERE 1 = 1";
//
//        Map<String, Object> map = new HashMap<>();
//
//        // 查詢條件
//        sql = addFilteringSql(sql, map, orderQueryParams);
//
//        // 排序
//        sql = sql + " ORDER BY created_date DESC";
//
//        // 分頁
//        sql = sql + " LIMIT :limit OFFSET :offset";
//        map.put("limit", orderQueryParams.getLimit());
//        map.put("offset", orderQueryParams.getOffset());
//
//        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
//
//        return orderList;
//    }
//
//    @Override
//    public Order getOrderById(Integer orderId) {
//        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date " +
//                "FROM `order` WHERE order_id = :orderId";
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("orderId", orderId);
//
//        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
//
//        if (orderList.isEmpty()) {
//            return null;
//        }
//
//        return orderList.get(0);
//    }
//
//    @Override
//    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
//        String sql = "SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, " +
//                "p.product_name, p.image_url FROM order_item AS oi " +
//                "LEFT JOIN product AS p ON oi.product_id = p.product_id " +
//                "WHERE oi.order_id = :orderId";
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("orderId", orderId);
//
//        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());
//
//        return orderItemList;
//    }
//
//    @Override
//    public Integer createOrder(Integer userId, Integer totalAmount) {
//        String sql = "INSERT INTO `order`(user_id, total_amount) VALUES (:userId, :totalAmount)";
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("userId", userId);
//        map.put("totalAmount", totalAmount);
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
//
//        Integer orderId = keyHolder.getKey().intValue();
//
//        return orderId;
//    }
//
//    @Override
//    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
//        String sql = "INSERT INTO order_item(order_id, product_id, quantity, amount) " +
//                "VALUES (:orderId, :productId, :quantity, :amount)";
//
//        // 使用batchUpdate 一次加入數據，效率較高
//
//        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];
//
//        for (int i = 0; i < orderItemList.size(); i++) {
//            OrderItem orderItem = orderItemList.get(i);
//
//            parameterSources[i] = new MapSqlParameterSource();
//            parameterSources[i].addValue("orderId", orderId);
//            parameterSources[i].addValue("productId", orderItem.getProductId());
//            parameterSources[i].addValue("quantity", orderItem.getQuantity());
//            parameterSources[i].addValue("amount", orderItem.getAmount());
//        }
//
//        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
//    }
//
//    private String addFilteringSql(String sql, Map<String, Object> map, OrderQueryParams orderQueryParams) {
//        if (orderQueryParams.getUserId() != null) {
//            sql = sql + " AND user_id = :userId";
//            map.put("userId", orderQueryParams.getUserId());
//        }
//
//        return sql;
//    }
//}
