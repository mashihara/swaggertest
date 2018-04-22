---
name: spring swagger、springfox入門
number: 256
wip: true
---

## 参考
- https://www.qoosky.io/techs/3cc94466c8
- https://qiita.com/disc99/items/37228f5d687ad2969aa2

# 設定
## 1.spring new project
- web
- lombok
- devtool

## 2. build.gradle
### 追加したところ
```build.gradle
	compile group: 'io.springfox', name: 'springfox-swagger2', version: '2.8.0'
	compile group: 'io.springfox', name: 'springfox-swagger-ui', version: '2.8.0'
```
### 全体
```build.gradle
buildscript {
	ext {
		springBootVersion = '2.0.1.RELEASE'
	}
	repositories {
		mavenCentral()
	}
	dependencies {
		classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
	}
}

apply plugin: 'java'
apply plugin: 'eclipse'
apply plugin: 'org.springframework.boot'
apply plugin: 'io.spring.dependency-management'

group = 'com.group.swaggertest'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = 1.8

repositories {
	mavenCentral()
}

dependencies {
	compile('org.springframework.boot:spring-boot-starter-web')
	runtime('org.springframework.boot:spring-boot-devtools')
	compileOnly('org.projectlombok:lombok')
	testCompile('org.springframework.boot:spring-boot-starter-test')
	compile group: 'io.springfox', name: 'springfox-swagger2', version: '2.8.0'
	compile group: 'io.springfox', name: 'springfox-swagger-ui', version: '2.8.0'
}
```


## 3.クラス定義
```Court.java
package net.mashihara.domain;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Court {
	int id;
	String name;	
}
```
```CourtController.java
package net.mashihara;

@RestController
public class CourtController {
	@GetMapping("/court/{id}")
	public Court getCourt(@PathVariable int id) {
		return new Court(id,"有明");
	}
	@PostMapping("/court/{id}")
	public Court changeCourt(@PathVariable int id,@RequestBody Court court) {
		court.setId(id);
		return court;
	}
}
```

```SwaggerConfiguration.java
package net.mashihara;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
class Config {
    @Bean
    public Docket admin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("admin")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiinfo());
    }

    private ApiInfo apiinfo() {
        return new ApiInfoBuilder()
                .title("テニスAPI")
                .description("テニスコート情報を扱うためのAPIです。")
                .version("1.0")
                .contact(new Contact("mashihara","", ",mashiharah@nttdata.co.jp"))
                .build();
    }
}
```

# 結果
http://localhost:8080/swagger-ui.html
![image.png (151.5 kB)](https://img.esa.io/uploads/production/attachments/6094/2018/04/22/21094/d98b7b5b-ef0e-47fc-b55d-bc11ee54f58f.png)

http://localhost:8080/v2/api-docs?group=admin
![image.png (202.8 kB)](https://img.esa.io/uploads/production/attachments/6094/2018/04/22/21094/15bd9d46-98c0-4e9f-8b31-52dc9cfd0ad1.png)
