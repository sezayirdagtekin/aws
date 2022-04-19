package com.sezo.demo.util;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.TableStatus;

public class Utils {

    public static void createTable(Class<?> domainClass, AmazonDynamoDB dynamoDB) {
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(dynamoDB);

        CreateTableRequest createTableRequest
                = dynamoDBMapper.generateCreateTableRequest(domainClass);
        createTableRequest.withProvisionedThroughput(
                new ProvisionedThroughput(
                        1L,
                        1L
                )
        );

        if (tableExists(dynamoDB, createTableRequest.getTableName())) {
            System.out.println(
                    String.format(
                            "Table for class %s already exists.",
                            domainClass.getName()
                    )
            );
            return;
        }

        System.out.println(
                String.format(
                        "Table for class %s does not exist. Creating.",
                        domainClass.getName()
                )
        );
        dynamoDB.createTable(createTableRequest);
        waitForTableCreated(createTableRequest.getTableName(), dynamoDB);
    }

    private static void waitForTableCreated(String tableName, AmazonDynamoDB dynamoDB) {
        while (true) {
            try {
                System.out.println("Table not created yet. Waiting 2000 ms");
                Thread.sleep(2000);
                TableDescription table = dynamoDB.describeTable(tableName).getTable();
                if (table == null)
                    continue;

                String tableStatus = table.getTableStatus();
                if (tableStatus.equals(TableStatus.ACTIVE.toString())) {
                    System.out.println("Table is created and active");
                    return;
                }

            } catch (ResourceNotFoundException ex) {
                System.out.print("Table still not created. Waiting.");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private static boolean tableExists(AmazonDynamoDB dynamoDB, String tableName) {
        try {
            dynamoDB.describeTable(tableName);
            return true;
        } catch (ResourceNotFoundException ex) {
            return false;
        }
    }

  
}

