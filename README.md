Solution was the H2 version. We upgraded also H2 to 2.0 but that version is not compatible with Spring Batch 4.3.4. Downgrade to H2 version 1.4.200 solved the issue.

Uses jpa specifications sources:
https://stackoverflow.com/questions/71365002/spring-jpa-join-and-orderby-in-specification
