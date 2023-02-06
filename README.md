Solution was the H2 version. We upgraded also H2 to 2.0 but that version is not compatible with Spring Batch 4.3.4. Downgrade to H2 version 1.4.200 solved the issue.

Uses jpa specifications sources:
https://stackoverflow.com/questions/71365002/spring-jpa-join-and-orderby-in-specification
criteria builder
https://docs.oracle.com/cd/E19226-01/820-7627/gjitv/index.html
https://en.wikibooks.org/wiki/Java_Persistence/Criteria