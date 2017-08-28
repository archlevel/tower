### 多线程工具包
+ SynMAsynBizExecutor（分布式并发处理框架）
	
	+ demo
	
		ConcurrentHashMap<Integer, Object> map = new ConcurrentHashMap<Integer, Object>();
		
		long start = System.currentTimeMillis();
		
		new SynMAsynBizExecutor(map, "test1", 1) {
        
			public void execute() {

			try {
                Thread.sleep(100);
                
            } catch (InterruptedException e) {}
            
            this.setResultRef(getIndex());
        	}
		};

    	new SynMAsynBizExecutor(map, "test2", 1) {
        
			public void execute() {

			try {
                Thread.sleep(80);
                
            } catch (InterruptedException e) {}
            
            this.setResultRef(getIndex());
        	}
		};

   		new SynMAsynBizExecutor(map, "test3", 1) {
        
			public void execute() {

			try {
                Thread.sleep(110);
                
            } catch (InterruptedException e) {}
            
            this.setResultRef(getIndex());
        	}
		};
		
		SynMAsynBizHelper.errorCheck(start, map);
		

+ BSynMAsynBizExecutor（分页并发处理器）
	
	+ Demo
	
		List<Integer> datas = new ArrayList<Integer>();
    
        datas.add(1);
    
        datas.add(2);
    
        datas.add(3);
    
        datas.add(4);
    
        datas.add(5);
    
        datas.add(6);
    
        datas.add(7);

        new BSynMAsynBizExecutor<Integer>("test", 5, datas) {
            
            public void executes(Integer object) {
            
                try {
                
                    long time = Double.valueOf(Math.random() * 1000).longValue();
                    
                    Thread.sleep(time);
                    
                } catch (InterruptedException e) {
                    
                    logger.error(e);
                    
                }
                
            }
            
        };
        
+ SynLockerExecutor（分布式同步锁）
		
	+ Demo
	
		Thread t = new Thread() {
		
            public void run() {
            
                int idx = 0;
                
                while (true) {
                
                    RequestID.set(Thread.currentThread().getName() + "@" + (++idx));
                    
                    try {
                    
                        new SynLockerExecutor(SynLockerExecutor.class, "test0", 1, true) {
                        
                            public void execute() {
                            
                                try {
                                
                                    System.err.println(RequestID.get() + " before " + "@"
                                            + System.currentTimeMillis());
                                            
                                    long t = Double.valueOf(Math.random() * 100).longValue();
                                    Thread.currentThread().sleep(t);
                                    
                                    System.err.println(RequestID.get() + " exec used: " + t
                                            + " after " + "@" + System.currentTimeMillis());
                        
                                } catch (InterruptedException e) {
                                
                                    logger.error(e);
                                    
                                }
                                
                            }
                            
                        };
                        
                    } catch (Exception ex) {
                    
                        logger.error(ex);
                        
                    }
                    
                }
                
            }
            
        };
        
        t.start();
        
        t = new Thread() {
		
            public void run() {
            
                int idx = 0;
                
                while (true) {
                
                    RequestID.set(Thread.currentThread().getName() + "@" + (++idx));
                    
                    try {
                    
                        new SynLockerExecutor(SynLockerExecutor.class, "test0", 1, true) {
                        
                            public void execute() {
                            
                                try {
                                
                                    System.err.println(RequestID.get() + " before " + "@"
                                            + System.currentTimeMillis());
                                            
                                    long t = Double.valueOf(Math.random() * 100).longValue();
                                    Thread.currentThread().sleep(t);
                                    
                                    System.err.println(RequestID.get() + " exec used: " + t
                                            + " after " + "@" + System.currentTimeMillis());
                        
                                } catch (InterruptedException e) {
                                
                                    logger.error(e);
                                    
                                }
                                
                            }
                            
                        };
                        
                    } catch (Exception ex) {
                    
                        logger.error(ex);
                        
                    }
                    
                }
                
            }
            
        };
        
        t.start();
        
        while (true) {
        
            try {
            
                Thread.currentThread().sleep(100);
                
            } catch (InterruptedException e) {
            
                logger.error(e);
                
            }
            
        }
    

