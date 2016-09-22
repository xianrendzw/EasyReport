----- 1.0 的reporting表转 2.0 ezrpt_meta_report 表的sql
SELECT
  id,
  uid,
  pid category_id,
  ds_id,
  name,
  sql_text,
  meta_columns,
  query_params,
  concat('{\"layout\":',layout,',\"statColumnLayout\":',stat_column_layout,',\"dataRange\":',data_range,'}') as options,
  status,
  sequence,
  comment,
  create_user,
  create_time gmt_created,
  update_time gmt_modified
FROM reporting
WHERE
  pid <> 0