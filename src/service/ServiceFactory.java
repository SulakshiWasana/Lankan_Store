package service;

import service.custom.impl.*;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;

    private ServiceFactory() {
    }

    public static ServiceFactory getBOFactory() {
        if (serviceFactory == null) {
            serviceFactory = new ServiceFactory();
        }
        return serviceFactory;
    }

    public SuperService getBO(BoTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerServiceImpl();
            case ITEM:
                return new ItemServiceImpl();
            case ORDER:
                return new OrderServiceImpl();
            case ITEMDETAIL:
                return new ItemDeatilsServiceImpl();
            case PURCHASE_ORDER:
                return new PurchaseOrderServiceImpl();
            default:
                return null;
        }
    }

    public enum BoTypes {
        CUSTOMER, ITEM, ORDER, ITEMDETAIL, PURCHASE_ORDER
    }
}
