# Đặc tả mục tiêu đồ án: Chợ dịch vụ số và hồ sơ người làm

> Tài liệu định hướng sản phẩm, chức năng và mô hình dữ liệu để tham khảo khi phát triển. Đây là đặc tả, chưa phải mã nguồn hay SQL chạy được. Tên trường dùng snake_case theo hướng MySQL.

## 1. Mục tiêu

Xây dựng website kết nối khách hàng với người cung cấp dịch vụ số (ví dụ: thiết kế, lập trình, viết nội dung). Khách hàng tìm dịch vụ, xem hồ sơ người bán, đặt dịch vụ, trao đổi yêu cầu và gửi tệp trong hội thoại/đơn hàng. Người bán quản lý hồ sơ, dịch vụ, đơn hàng và giao sản phẩm. Quản trị viên quản lý tài khoản, danh mục và nội dung vi phạm.

Sản phẩm có thể bắt đầu từ portfolio hiện tại, sau đó phát triển thành marketplace. Mỗi tài khoản có thể vừa mua vừa bán; quyền quản trị được cấp riêng.

## 2. Vai trò và chức năng

### Khách chưa đăng nhập
- Xem trang chủ, danh mục, tìm kiếm/lọc dịch vụ.
- Xem trang dịch vụ và hồ sơ công khai của người bán.
- Đăng ký, đăng nhập, khôi phục mật khẩu.

### Thành viên / khách hàng
- Quản lý hồ sơ cá nhân.
- Lưu dịch vụ yêu thích (có thể làm sau).
- Đặt dịch vụ, cung cấp yêu cầu, theo dõi trạng thái đơn và lịch sử.
- Nhắn tin với người bán trong hội thoại gắn với đơn hàng; gửi/nhận tệp đính kèm.
- Xác nhận hoàn thành, yêu cầu chỉnh sửa hoặc hủy theo quy tắc đồ án.
- Đánh giá người bán sau khi đơn hoàn tất.

### Người bán
- Tạo hồ sơ người bán, mô tả kinh nghiệm, kỹ năng, ảnh đại diện và portfolio.
- Tạo/sửa/ẩn dịch vụ; chọn danh mục, giá, thời gian giao và các gói dịch vụ.
- Nhận hoặc từ chối đơn; trao đổi yêu cầu; gửi bản giao hàng/tệp; đánh dấu đã giao.
- Xem đánh giá và lịch sử đơn.

### Quản trị viên
- Quản lý tài khoản, khóa/mở khóa tài khoản.
- Quản lý danh mục dịch vụ.
- Ẩn dịch vụ, đánh giá hoặc tệp bị báo cáo; xem nhật ký thao tác.
- Theo dõi đơn hàng và xử lý tranh chấp ở mức mô phỏng.

## 3. Thực thể và dữ liệu đề xuất

Các trường `id`, `created_at`, `updated_at` được dùng chung khi phù hợp. Khóa ngoại nên được tạo index. Mật khẩu chỉ lưu dạng hash mạnh, không lưu mật khẩu thô.

### 3.1. Tài khoản và hồ sơ

**users** — tài khoản đăng nhập: `id`, `email` (duy nhất), `password_hash`, `status` (PENDING/ACTIVE/LOCKED), `created_at`, `updated_at`, `last_login_at`.

**user_profiles** — thông tin công khai/cá nhân: `id`, `user_id` (duy nhất), `display_name`, `avatar_file_id` (nullable), `bio`, `country`, `timezone`, `created_at`, `updated_at`.

**roles** — vai trò: `id`, `code` (ADMIN/USER), `name`.

**user_roles** — gán vai trò: `user_id`, `role_id`; khóa chính ghép. Một thành viên có thể vừa mua vừa bán; BUYER/SELLER là cách sử dụng, không nhất thiết là quyền loại trừ nhau.

**seller_profiles** — thiết lập người bán: `id`, `user_id` (duy nhất), `headline`, `description`, `languages`, `response_time_hours`, `is_accepting_orders`, `created_at`, `updated_at`.

### 3.2. Portfolio của người bán

**portfolio_projects** — dự án giới thiệu: `id`, `user_id`, `title`, `description`, `github_url`, `demo_url`, `cover_file_id` (nullable), `sort_order`, `created_at`.

**skills** — danh mục kỹ năng dùng chung: `id`, `name` (duy nhất).

**user_skills** — kỹ năng của thành viên: `user_id`, `skill_id`, `level` (nullable); khóa chính ghép.

**certificates** — chứng chỉ: `id`, `user_id`, `name`, `issuer`, `issued_at`, `credential_url`, `file_id` (nullable).

**awards** — giải thưởng: `id`, `user_id`, `name`, `issuer`, `awarded_at`, `description`.

### 3.3. Danh mục và dịch vụ

**categories** — danh mục phân cấp: `id`, `parent_id` (nullable), `name`, `slug` (duy nhất), `description`, `is_active`.

**services** — dịch vụ/gig do người bán đăng: `id`, `seller_user_id`, `category_id`, `title`, `slug`, `description`, `status` (DRAFT/ACTIVE/PAUSED/REMOVED), `delivery_days`, `created_at`, `updated_at`.

**service_packages** — các gói của một dịch vụ: `id`, `service_id`, `name` (ví dụ Basic/Standard/Premium), `description`, `price`, `delivery_days`, `revision_limit`, `is_active`. Mỗi dịch vụ có thể có một hoặc nhiều gói.

**service_media** — ảnh/clip giới thiệu dịch vụ: `id`, `service_id`, `file_id`, `sort_order`.

**favorites** — dịch vụ yêu thích: `user_id`, `service_id`, `created_at`; khóa chính ghép.

### 3.4. Đơn hàng và giao nhận

**orders** — đơn hàng: `id`, `order_number` (duy nhất), `buyer_user_id`, `seller_user_id`, `status` (PENDING/ACCEPTED/IN_PROGRESS/DELIVERED/REVISION_REQUESTED/COMPLETED/CANCELLED/DISPUTED), `total_amount`, `currency`, `buyer_requirements`, `ordered_at`, `due_at`, `completed_at`, `created_at`, `updated_at`.

**order_items** — ảnh chụp gói dịch vụ tại lúc đặt: `id`, `order_id`, `service_id` (nullable nếu dịch vụ bị gỡ), `package_id` (nullable), `service_title_snapshot`, `package_name_snapshot`, `quantity`, `unit_price`, `delivery_days_snapshot`. Lưu snapshot để thay đổi giá dịch vụ sau này không làm sai lịch sử đơn.

**order_deliveries** — các lần giao hàng/bản sửa: `id`, `order_id`, `seller_user_id`, `message`, `delivery_number`, `created_at`.

**order_delivery_files** — tệp thuộc lần giao hàng: `delivery_id`, `file_id`; khóa chính ghép.

**order_events** — lịch sử trạng thái: `id`, `order_id`, `actor_user_id` (nullable), `from_status`, `to_status`, `note`, `created_at`.

**payments** — thông tin thanh toán mô phỏng: `id`, `order_id`, `amount`, `currency`, `status` (PENDING/PAID/FAILED/REFUNDED), `method`, `transaction_reference` (nullable), `paid_at`, `created_at`. Không lưu số thẻ hoặc dữ liệu bí mật thanh toán. Với đồ án có thể mô phỏng thanh toán, không tích hợp cổng thật.

### 3.5. Nhắn tin và tệp

**conversations** — hội thoại: `id`, `order_id` (nullable; duy nhất nếu gắn với một đơn), `created_at`, `updated_at`. MVP nên tạo hội thoại khi khách liên hệ hoặc đặt hàng; hội thoại của đơn phải có đúng các bên liên quan.

**conversation_participants** — thành viên hội thoại: `conversation_id`, `user_id`, `joined_at`, `last_read_at`; khóa chính ghép. Dùng để xác định ai được đọc/gửi tin.

**messages** — tin nhắn: `id`, `conversation_id`, `sender_user_id`, `body` (có thể rỗng nếu chỉ gửi tệp), `message_type` (TEXT/FILE/SYSTEM), `created_at`, `edited_at` (nullable), `deleted_at` (nullable). Mỗi tin phải do thành viên hội thoại gửi.

**message_attachments** — tệp đính kèm tin nhắn: `message_id`, `file_id`; khóa chính ghép.

**files** — metadata tệp đã tải lên: `id`, `owner_user_id`, `storage_key` hoặc `storage_path`, `original_name`, `mime_type`, `size_bytes`, `checksum` (nullable), `visibility` (PRIVATE/PUBLIC), `created_at`, `deleted_at` (nullable). Cơ sở dữ liệu chỉ lưu metadata và khóa lưu trữ; nội dung tệp nằm trong ổ đĩa/object storage. Tệp đơn hàng/tin nhắn phải kiểm tra quyền truy cập khi tải xuống, không dùng URL công khai đoán được.

Luồng gửi tệp: thành viên chọn tệp → máy chủ kiểm tra quyền, dung lượng và loại tệp → lưu tệp vào storage → tạo bản ghi `files` → tạo `messages` và `message_attachments` (hoặc gắn vào lần giao hàng). Khi tải xuống, máy chủ xác minh người dùng thuộc hội thoại/đơn tương ứng.

### 3.6. Đánh giá, thông báo, hỗ trợ

**reviews** — đánh giá sau đơn hoàn tất: `id`, `order_id` (duy nhất), `reviewer_user_id`, `reviewed_user_id`, `rating` (1–5), `comment`, `status` (VISIBLE/HIDDEN), `created_at`. Chỉ bên mua của đơn COMPLETED được tạo đánh giá cho bên bán.

**notifications** — thông báo trong ứng dụng: `id`, `user_id`, `type`, `title`, `body`, `target_type`, `target_id`, `read_at`, `created_at`. Dùng cho tin nhắn mới, đơn mới, thay đổi trạng thái và đánh giá.

**reports** — báo cáo nội dung/tài khoản: `id`, `reporter_user_id`, `target_type`, `target_id`, `reason`, `status` (OPEN/REVIEWED/RESOLVED/REJECTED), `handled_by_user_id` (nullable), `created_at`, `resolved_at`.

**audit_logs** — nhật ký hành động quản trị/quan trọng: `id`, `actor_user_id` (nullable), `action`, `target_type`, `target_id`, `details` (JSON nullable), `created_at`.

## 4. Quan hệ chính

- `users` 1–1 `user_profiles`; `users` 0–1 `seller_profiles`.
- `users` N–N `roles` qua `user_roles`.
- `users` 1–N `services`, `portfolio_projects`, `certificates`, `awards`.
- `services` N–1 `categories`; `services` 1–N `service_packages` và `service_media`.
- `users` (buyer) 1–N `orders`; `users` (seller) 1–N `orders`.
- `orders` 1–N `order_items`, `order_events`, `order_deliveries`; `orders` 0–N `payments`; đơn hoàn tất có tối đa một `review`.
- `conversations` 1–N `conversation_participants` và `messages`; `messages` N–N `files` qua `message_attachments`.
- `files` có thể được tham chiếu bởi avatar, portfolio, chứng chỉ, media dịch vụ và tệp giao hàng.

## 5. Quy tắc nghiệp vụ cần giữ

1. Email tài khoản là duy nhất; tài khoản bị khóa không thể đăng nhập/gửi tin/đặt dịch vụ.
2. Người bán chỉ sửa dịch vụ của mình; dịch vụ ACTIVE mới được đặt.
3. Người mua và người bán trên đơn không được là cùng một tài khoản.
4. Giá, tên gói và thời gian giao phải được chụp vào `order_items` lúc đặt.
5. Chỉ người tham gia hội thoại được đọc tin và tải tệp của hội thoại đó.
6. Chỉ người mua của đơn đã COMPLETED được đánh giá; mỗi đơn chỉ có một đánh giá.
7. Thay đổi trạng thái đơn được kiểm soát theo luồng và ghi vào `order_events`.
8. Giới hạn loại/kích thước tệp ở máy chủ; tên gốc chỉ để hiển thị, không dùng làm đường dẫn lưu trữ.
9. Dùng transaction cho các thao tác tạo đơn, gói đơn và tạo hội thoại liên quan để tránh dữ liệu dở dang.
10. Các tiền tệ lưu bằng DECIMAL, không dùng số thực; thời gian lưu nhất quán (UTC) và hiển thị theo múi giờ người dùng.

## 6. Phạm vi theo giai đoạn

### Giai đoạn 1 — nền tảng portfolio và tài khoản
- Đăng ký/đăng nhập, hồ sơ, quyền cơ bản.
- Portfolio dự án, kỹ năng, chứng chỉ và giải thưởng.
- Phân quyền người dùng; bảo vệ dữ liệu cá nhân.

### Giai đoạn 2 — marketplace cơ bản
- Danh mục, tìm kiếm, trang dịch vụ, gói dịch vụ.
- Tạo đơn và trạng thái đơn; thanh toán mô phỏng.
- Hồ sơ người bán và lịch sử mua/bán.

### Giai đoạn 3 — nhắn tin và file
- Hội thoại theo người dùng/đơn hàng, danh sách thành viên, trạng thái đã đọc.
- Tin nhắn văn bản và tệp đính kèm; kiểm soát quyền truy cập tệp.
- Tệp giao hàng, yêu cầu chỉnh sửa và hoàn tất đơn.

### Giai đoạn 4 — chất lượng và quản trị
- Đánh giá, thông báo, báo cáo vi phạm, giao diện quản trị.
- Nhật ký hoạt động, kiểm thử phân quyền, sao lưu và triển khai.

## 7. Ghi chú khi chuyển từ mã hiện tại

Mã hiện tại mới có entity `Project` và API CRUD cho portfolio; các lớp `User`, `Skill`, `Certificate`, `Award` đang là khung trống. Tài liệu này mở rộng mục tiêu đồ án thành marketplace nhiều tài khoản. Cần cập nhật entity, repository, service, controller, xác thực/phân quyền và cấu hình database theo từng giai đoạn. Không nên tạo tất cả bảng rồi coi như chức năng đã hoạt động.
